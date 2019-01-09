package com.kwin.sell.sell.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwin.sell.sell.model.ProductCategory;
import com.kwin.sell.sell.repository.ProductCategoryRepository;
import com.kwin.sell.sell.service.CategoryService;

/**
 * Product类目实现
 * @author Kwin
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ProductCategoryRepository repository;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		return repository.findById(categoryId).get();
	}

	@Override
	public List<ProductCategory> findAll() {
		return repository.findAll();
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return repository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return repository.save(productCategory);
	}

}
