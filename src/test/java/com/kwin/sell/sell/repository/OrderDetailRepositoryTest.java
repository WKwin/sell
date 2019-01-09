package com.kwin.sell.sell.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.sell.model.OrderDetail;

/**
 * 订单详情dao测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository repository;
	
	@Test
	public void testSave() {
		OrderDetail order = new OrderDetail();
		order.setDetailId("1234567811");
		order.setOrderId("1234567");
		order.setProductIcon("http://xxxxx.jpg");
		order.setProductId("123457");
		order.setProductName("皮皮虾");
		order.setProductQuantity(2);
		order.setProductPrice(new BigDecimal(6.4));
		
		OrderDetail result = repository.save(order);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testFindByOrderId() {
		List<OrderDetail> results = repository.findByOrderId("123456");
		Assert.assertNotEquals(0, results.size());
	}

}
