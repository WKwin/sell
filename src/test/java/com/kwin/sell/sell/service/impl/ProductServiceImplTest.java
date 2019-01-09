package com.kwin.sell.sell.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.common.enums.ProductStatusEnum;
import com.kwin.sell.sell.model.ProductInfo;

/**
 * 产品信息service实现test
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl productInfoService;
	@Test
	public void testFindOne() {
		ProductInfo result = productInfoService.findOne("123456");
		Assert.assertEquals("123456", result.getProductId());
	}

	@Test
	public void testFindUpAll() {
		List<ProductInfo> results = productInfoService.findUpAll();
		Assert.assertNotEquals(0, results.size());
	}

	@Test
	public void testFindAll() {
		PageRequest request =PageRequest.of(0, 2);
		Page<ProductInfo> page = productInfoService.findAll(request);
//		System.out.println(page.getTotalElements());
        Assert.assertNotEquals(0, page.getTotalElements());
	}

	@Test
	public void testSave() {
		ProductInfo productInfo = new ProductInfo();
		
		 productInfo.setProductId("123457");
	     productInfo.setProductName("皮皮虾");
	     productInfo.setProductPrice(new BigDecimal(3.2));
	     productInfo.setProductStock(100);
	     productInfo.setProductDescription("很好吃的虾");
	     productInfo.setProductIcon("http://xxxxx.jpg");
	     productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
	     productInfo.setCategoryType(2);
	     
	     ProductInfo result = productInfoService.save(productInfo);
	     Assert.assertNotNull(result);
	}

}
