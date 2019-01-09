package com.kwin.sell.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwin.sell.sell.service.SecKillService;

import lombok.extern.slf4j.Slf4j;

/**
 * 抢购活动，为了压测
 * @author Kwin
 *
 */
@RestController
@RequestMapping("/secKill")
@Slf4j
public class SecKillController {
	@Autowired
	SecKillService secKillService;

	/**
	 * 查询秒杀活动特价商品信息
	 * @param productId
	 * @return
	 */
	@GetMapping("/query/{productId}")
	public String query(@PathVariable String productId) {
		
		return secKillService.querySecKillProductInfo(productId);
	}
	
	/**
	 * 秒杀，没有抢到获得“哎呦喂，*****”，抢到了会返回剩余库存量
	 * @param productId
	 * @return
	 */
	@GetMapping("/order/{productId}")
	public String skill(@PathVariable String productId) {
		log.info("@skill request, productId:" + productId);
		secKillService.orderProductMockDiffUser(productId);
		return secKillService.querySecKillProductInfo(productId);
	}
}
