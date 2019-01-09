package com.kwin.sell.sell.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwin.sell.sell.model.OrderMaster;

/**
 * 订单主表dao测试
 * @author Kwin
 *
 */
@RunWith(SpringRunner.class)//底层使用JUnit测试工具
@SpringBootTest//启动spring的工程
public class OrderMasterRepositoryTest {
	@Autowired
	OrderMasterRepository repository;

	private final String OPENID = "123123";
	
	@Test
	public void testSave() {
		OrderMaster order = new OrderMaster();
		order.setOrderId("1234567");
		order.setBuyerName("Kwin");
		order.setBuyerPhone("12345678978");
		order.setBuyerAddress("test");
		order.setBuyerOpenid("123123");
		order.setOrderAmount(new BigDecimal(2.5));
		
		OrderMaster result = repository.save(order);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testFindByBuyerOpenid() {
		PageRequest request = PageRequest.of(1, 3);
		Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, request);
		Assert.assertNotEquals(0, result.getTotalElements());
	}

}
