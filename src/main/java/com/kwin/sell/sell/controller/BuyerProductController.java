package com.kwin.sell.sell.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwin.sell.common.utils.ResultVOUtil;
import com.kwin.sell.sell.model.ProductCategory;
import com.kwin.sell.sell.model.ProductInfo;
import com.kwin.sell.sell.repository.ProductInfoRepository;
import com.kwin.sell.sell.service.CategoryService;
import com.kwin.sell.sell.service.ProductService;
import com.kwin.sell.sell.vo.ProductInfoVO;
import com.kwin.sell.sell.vo.ProductVO;
import com.kwin.sell.sell.vo.ResultVO;

/**
 * 买家商品
 * @author Kwin
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	@Cacheable(cacheNames = "product", key = "123", unless = "#result.getCode() != 0")//返回对象code值为0时，才缓存
	public ResultVO list() {
		//1.查询所有上架商品
		List<ProductInfo> productInfos = productService.findUpAll();
		
		//2.查询类目（一次性查询）
		List<Integer> categoryTypeList = new ArrayList<>();
		//传统方法
		/*for (ProductInfo productInfo : productInfos) {
			categoryTypeList.add(productInfo.getCategoryType());
		}*/
		//精简方法(Java8, lambda)
		categoryTypeList = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
		
		List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);
		
		//3.数据拼装
		List<ProductVO> productVOs = new ArrayList<>();
		for(ProductCategory productCategory : productCategories) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryType(productCategory.getCategoryType());
			productVO.setCategoryName(productCategory.getCategoryName());

			List<ProductInfoVO> productInfoVOs = new ArrayList<ProductInfoVO>();
			for(ProductInfo productInfo : productInfos) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOs.add(productInfoVO);
				}
			}
			
			productVO.setProductInfoVOs(productInfoVOs);
			productVOs.add(productVO);
		}

//		ResultVO resultVO = ResultVOUtil.success(productVOs);
		return ResultVOUtil.success(productVOs);
	}
}
