package com.kwin.sell.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwin.sell.sell.model.SellerInfo;
import com.kwin.sell.sell.repository.SellerInfoRepository;
import com.kwin.sell.sell.service.SellerService;

/**
 * 
 * @author Kwin
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
