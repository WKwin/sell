package com.kwin.sell.sell.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kwin.sell.sell.dto.CartDTO;
import com.kwin.sell.sell.model.ProductInfo;
/**
 * 产品信息service接口
 * @author Kwin
 *
 */
public interface ProductService {
	ProductInfo findOne(String productId);
	
	/**
	 * 查询所有在架的商品列表
	 * @return
	 */
	List<ProductInfo> findUpAll();
	
	/**
	 * 查询所有的商品列表
	 * @return
	 */
	Page<ProductInfo> findAll(Pageable pageable);
	
	ProductInfo save(ProductInfo productInfo);
	
	//加库存
	void increaseStock(List<CartDTO> cartDTOList);
	
	//减库存
	void decreaseStock(List<CartDTO> cartDTOList);
	
    /**
     * 上架
     *
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     *
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);
}
