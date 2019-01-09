package com.kwin.sell.sell.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.common.enums.OrderStatusEnum;
import com.kwin.sell.common.enums.PayStatusEnum;
import com.kwin.sell.sell.dto.OrderDTO;
import com.kwin.sell.sell.model.OrderDetail;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单Service测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
@Slf4j
public class OrderServiceImplTest {

	@Autowired
	private OrderServiceImpl orderService;
	
	private final String OPENID = "1231234";
	
	private final String ORDER_ID = "1542209813288665390";
	
	@Test
	public void testCreate() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName("Kwin2");
		orderDTO.setBuyerAddress("无锡滨湖区江南大学");
		orderDTO.setBuyerPhone("18861821002");
		orderDTO.setBuyerOpenid(OPENID);
		
		//购物车
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProductId("123456");
		orderDetail.setProductQuantity(2);
		
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setProductId("123457");
		orderDetail2.setProductQuantity(3);
		orderDetailList.add(orderDetail);
		orderDetailList.add(orderDetail2);
		
		orderDTO.setOrderDetailList(orderDetailList);
		OrderDTO result = orderService.create(orderDTO);
		log.info("【创建订单】result={}",result);
		Assert.assertNotNull(result);
	}

	@Test
	public void testFindOne() {
		OrderDTO result = orderService.findOne(ORDER_ID);
		log.info("【查询单个订单】result={}",result);
		Assert.assertEquals(ORDER_ID, result.getOrderId());
	}

	@Test
	public void testFindList() {
		PageRequest request = PageRequest.of(0, 2);
		Page<OrderDTO> orderDTOPage = orderService.findList(OPENID, request);
		Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
	}

	@Test
	public void testCancel() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.cancel(orderDTO);
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
	}

	@Test
	public void testFinish() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.finish(orderDTO);
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	}

	@Test
	public void testPaid() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.paid(orderDTO);
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	}
	
	@Test
	public void findList() {
		PageRequest request = PageRequest.of(0, 2);
		Page<OrderDTO> orderDTOPage = orderService.findList(request);
//		Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
		Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
		
	}

}
