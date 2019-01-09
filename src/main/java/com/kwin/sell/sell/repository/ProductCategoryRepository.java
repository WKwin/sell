package com.kwin.sell.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwin.sell.sell.model.ProductCategory;
/**
 * Product类目dao
 * @author Kwin
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
