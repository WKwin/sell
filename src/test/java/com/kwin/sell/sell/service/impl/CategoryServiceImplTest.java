package com.kwin.sell.sell.service.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.sell.model.ProductCategory;

/**
 * Product类目Service测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class CategoryServiceImplTest {

	@Autowired
	CategoryServiceImpl categoryService;
	@Test
	public void findOne() {
		ProductCategory result = categoryService.findOne(1);
		Integer[] a = {1};
		Integer[] b = {result.getCategoryId()};
		Assert.assertArrayEquals(a, b);
	}

	@Test
	public void findAll() {
		List<ProductCategory> results = categoryService.findAll();
		Assert.assertNotEquals(0, results.size());
	}
	
	@Test
	public void findByCategoryTypeIn() {
		List<ProductCategory> results = categoryService.
				findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
		Assert.assertNotEquals(0, results.size());
	}
	
	@Test
	public void save() {
		ProductCategory productCategory = new ProductCategory("中性专享", 3);
		ProductCategory result = categoryService.save(productCategory);
		Assert.assertNotNull(result);
	}
}
