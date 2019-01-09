package com.kwin.sell.sell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kwin.sell.sell.model.OrderMaster;

/**
 * 订单主表dao
 * @author Kwin
 *
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
