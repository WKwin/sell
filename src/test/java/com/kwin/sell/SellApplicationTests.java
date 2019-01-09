package com.kwin.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellApplicationTests {
//	private final Logger logger = LoggerFactory.getLogger(SellApplicationTests.class);
	@Test
	public void contextLoads() {
//		logger.debug("test");
//		logger.info("test2");
//		logger.error("test3");
		String name = "kwin";
		String password = "123456";
		log.debug("debug");
		log.info("name: {}, passowrd: {}", name, password);
		log.error("error");
		log.warn("warn...");
	}

}
