package com.kwin.sell.sell.service;

import java.util.List;

import com.kwin.sell.sell.model.ProductCategory;

/**
 * Product类目接口
 * @author Kwin
 *
 */
public interface CategoryService {
	ProductCategory findOne(Integer categoryId);
	
	List<ProductCategory> findAll();
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
	
	ProductCategory save(ProductCategory productCategory);
	
}
