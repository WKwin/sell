package com.kwin.sell.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwin.sell.sell.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
	List<OrderDetail> findByOrderId(String orderId);
}
