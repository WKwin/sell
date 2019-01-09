package com.kwin.sell.sell.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 商品（包含类目）
 * @author Kwin
 *
 */
@Data
public class ProductVO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	/** 类目名字 */
	@JsonProperty("name")//json序列化返回到前端就是name了
	private String categoryName;
	
	/** 类目类型 */
	@JsonProperty("type")
	private Integer categoryType;
	
	@JsonProperty("foods")
	List<ProductInfoVO> productInfoVOs;
}
