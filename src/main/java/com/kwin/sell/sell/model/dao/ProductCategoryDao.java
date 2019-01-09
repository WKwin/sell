package com.kwin.sell.sell.model.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kwin.sell.sell.model.ProductCategory;
import com.kwin.sell.sell.model.mapper.ProductCategoryMapper;

/**
 * 
 * @author Kwin
 *
 */
public class ProductCategoryDao {
	@Autowired
	ProductCategoryMapper mapper;
	
	public int insertByMap(Map<String, Object> map) {
		return mapper.insertByMap(map);
	}
}
