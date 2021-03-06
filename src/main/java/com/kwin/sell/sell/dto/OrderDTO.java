package com.kwin.sell.sell.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kwin.sell.common.enums.OrderStatusEnum;
import com.kwin.sell.common.enums.PayStatusEnum;
import com.kwin.sell.common.utils.serializer.Date2LongSerializer;
import com.kwin.sell.sell.model.OrderDetail;
import com.kwin.sell.common.utils.EnumUtil;

import lombok.Data;

/**
 * 订单(Data Transfer Object) 数据传输对象 
 * @author Kwin
 *
 */
@Data
//@JsonInclude((JsonInclude.Include.NON_NULL))
public class OrderDTO {
    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    
    List<OrderDetail> orderDetailList; // = new ArrayList<>()
    
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
