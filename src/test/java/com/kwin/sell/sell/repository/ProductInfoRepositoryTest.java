package com.kwin.sell.sell.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.common.enums.ProductStatusEnum;
import com.kwin.sell.sell.model.ProductInfo;

/**
 * Product信息dao测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class ProductInfoRepositoryTest {
	@Autowired
	private ProductInfoRepository repository;

	@Test
	public void saveTest() {
		ProductInfo result = new ProductInfo();
		result.setProductId("123459");
		result.setProductName("荷包蛋");
		result.setProductPrice(new BigDecimal(10.5));
		result.setProductStock(80);
		result.setProductDescription("营养丰富，很美味");
		result.setProductIcon("http://xxx.jpg");
		result.setProductStatus(ProductStatusEnum.UP.getCode());
		result.setCategoryType(1);
		ProductInfo re = repository.save(result);
		Assert.assertNotNull(re);
	}
	
	@Test
	public void findByProductStatusTest() {
		List<ProductInfo> results = repository.
				findByProductStatus(ProductStatusEnum.UP.getCode());
		Assert.assertNotEquals(0, results.size());
	}

}
