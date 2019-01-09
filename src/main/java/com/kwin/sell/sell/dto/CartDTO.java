package com.kwin.sell.sell.dto;

import lombok.Data;

/**
 * 购物车
 * @author Kwin
 *
 */
@Data
public class CartDTO {
	
	/** 商品Id */
	private String productId;
	/** 数量 */
	private Integer productQuantity;
	public CartDTO(String productId, Integer productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	
}
