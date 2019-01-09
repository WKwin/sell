package com.kwin.sell.sell.repository;

import com.kwin.sell.sell.model.ProductCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Product类目dao测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class ProductCategoryRepositoryTest {
	@Autowired
	private ProductCategoryRepository repository;
	@Test
	public void findOneTest() {
		Optional<ProductCategory> productCategory = repository.findById(1);
		System.out.println(productCategory.get().toString());
	}
	@Test
	public void saveTest() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("女生最爱");
		productCategory.setCategoryType(3);
		repository.save(productCategory);
	}
	@Test
	//@Transactional //测试完后，数据回滚，数据库干净
	public void save2Test() {
		ProductCategory productCategory = new ProductCategory("热销榜", 1);
		ProductCategory result = repository.save(productCategory);
		//使用断言
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null, result);
	}
	@Test
	public void updateTest() {
		ProductCategory productCategory = repository.findById(2).get();
		productCategory.setCategoryType(3);
		repository.save(productCategory);
	}
	@Test
	public void findByCategoryTypeInTest() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		List<ProductCategory> result = repository.findByCategoryTypeIn(list);
		Assert.assertNotEquals(null, result.size());
	}
}
