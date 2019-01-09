package com.kwin.sell.sell.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kwin.sell.common.converter.OrderMaster2OrderDTOConverter;
import com.kwin.sell.common.enums.OrderStatusEnum;
import com.kwin.sell.common.enums.PayStatusEnum;
import com.kwin.sell.common.enums.ResultEnum;
import com.kwin.sell.common.exception.SellException;
import com.kwin.sell.common.utils.KeyUtil;
import com.kwin.sell.sell.dto.CartDTO;
import com.kwin.sell.sell.dto.OrderDTO;
import com.kwin.sell.sell.model.OrderDetail;
import com.kwin.sell.sell.model.OrderMaster;
import com.kwin.sell.sell.model.ProductInfo;
import com.kwin.sell.sell.repository.OrderDetailRepository;
import com.kwin.sell.sell.repository.OrderMasterRepository;
import com.kwin.sell.sell.service.OrderService;
import com.kwin.sell.sell.service.PayService;
import com.kwin.sell.sell.service.ProductService;
import com.kwin.sell.sell.service.PushMessageService;
import com.kwin.sell.sell.websocket.WebSocket;

import lombok.extern.slf4j.Slf4j;
/**
 * 订单业务实现类
 * @author Kwin
 *
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private WebSocket webSocket;

	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		//1.查询商品（库存，价格）
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			ProductInfo productInfo;
			try {
				productInfo = productService.findOne(orderDetail.getProductId());//查不到会抛异常
			} catch (Exception e2) {
				log.info("test");
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			//2.计算总价
//			orderDetail
			orderAmount = productInfo.getProductPrice()
									.multiply(new BigDecimal(orderDetail.getProductQuantity()))
									.add(orderAmount);
			//订单详情入库
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailRepository.save(orderDetail);
			
		}
		
		//3.写入订单数据库（orderMaster ，orderDetail）
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster, "orderStatus", "payStatus");//可以在实体中设置成静态常量
//		orderMaster.setOrderId(orderId);
		orderMaster.setOrderAmount(orderAmount);
//		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
//		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);
		
		//4.修改库存
		//后期使用redis的锁机制避免超卖问题
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> 
						new CartDTO(e.getProductId(), e.getProductQuantity())
						).collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);
		
		//发送websocket消息
		webSocket.sendMessage("有新的订单");
		
		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
		if (null == orderMaster) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetails)) {
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}
		
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetails);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		
		List<OrderDTO> orderDTOs = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		
		return new PageImpl<OrderDTO>(orderDTOs, pageable, orderMasterPage.getTotalElements());
	}

	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {//新订单才可以取消
			log.error("【取消订单】订单状态不正确，orderId = {}，orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (null == updateResult) {
			log.error("【取消订单】更新失败，orderMaster = {}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		//返还库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情，orderDTO = {}", orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productService.increaseStock(cartDTOList);
		
		//如果已支付，退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			payService.refund(orderDTO); 
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】订单状态不正确，orderId = {}，orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (null == updateResult) {
			log.error("【完结订单】更新失败，orderMaster = {}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		//推送微信模板消息
		pushMessageService.orderStatus(orderDTO);
		
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单支付】订单状态不正确，orderId = {}，orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付】订单支付状态不正确，orderDTO = {}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		
		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (null == updateResult) {
			log.error("【订单支付】更新失败，orderMaster = {}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
		
		List<OrderDTO> orderDTOs = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		
		return new PageImpl<OrderDTO>(orderDTOs, pageable, orderMasterPage.getTotalElements());
	}

}
