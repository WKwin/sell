package com.kwin.sell.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwin.sell.sell.model.SellerInfo;

/**
 * 买家信息表dao
 * @author Kwin
 *
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
