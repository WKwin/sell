package com.kwin.sell.sell.model.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.sell.model.ProductCategory;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
@Slf4j
public class ProductCategoryMapperTest {
	
	@Autowired
	ProductCategoryMapper mapper;
	@Test
	public void testInsertByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("category_name", "儿童专享");
		map.put("category_type", 5);
		int result = mapper.insertByMap(map);
		Assert.assertEquals(1, result);
	}

	@Test
	public void testInsertByObject() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("武林豪品");
		productCategory.setCategoryType(6);
		int result = mapper.insertByObject(productCategory);
		Assert.assertEquals(1, result);
	}
	
	@Test
	public void testFindByCategoryType() {
		ProductCategory productCategory = mapper.findByCategoryType(5);
		Assert.assertNotNull(productCategory);
	}
	
	@Test
	public void testFindByCategoryName() {
		List<ProductCategory> result = mapper.findByCategoryName("test");
	}

	@Test
    public void testUpdateByCategoryType() {
        int result = mapper.updateByCategoryType("武林佳酿", 6);
        Assert.assertEquals(1, result);
    }
	@Test
	public void testUpdateByObject() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("武林豪品");
		productCategory.setCategoryType(6);
		int result = mapper.updateByObject(productCategory);
		Assert.assertEquals(1, result);
	}
	
	@Test
	public void testDeleteByCategoryType() {
		 int result = mapper.deleteByCategoryType(6);
		 Assert.assertEquals(1, result);
	}
	
	/*
	 * 下面将sql写入xml文件中，使用mybatis
	 * 测试
	 */
	@Test
	public void test() {
		ProductCategory productCategory = mapper.getByCategoryType(5);
		Assert.assertNotNull(productCategory);
	}
}
