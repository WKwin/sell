package com.kwin.sell.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kwin.sell.sell.model.ProductInfo;

/**
 * product信息dao
 * @author Kwin
 *
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
	/**
	 * 
	 * @param productStatus  状态, 0正常1下架. 
	 * @return
	 */
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
