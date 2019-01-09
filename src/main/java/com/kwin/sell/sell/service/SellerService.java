package com.kwin.sell.sell.service;

import com.kwin.sell.sell.model.SellerInfo;

/**
 * 卖家端
 * @author Kwin
 *
 */
public interface SellerService {

	/**
	 * 通过openid查询卖家端信息
	 * @param openid
	 * @return
	 */
    SellerInfo findSellerInfoByOpenid(String openid);
}
