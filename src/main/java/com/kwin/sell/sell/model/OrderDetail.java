package com.kwin.sell.sell.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  订单详情
 * @author Kwin
 *
 */
@Entity
@Data
@DynamicUpdate //动态更新时间
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;
    
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}

