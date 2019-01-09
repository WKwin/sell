package com.kwin.sell.sell.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwin.sell.common.enums.ProductStatusEnum;
import com.kwin.sell.common.enums.ResultEnum;
import com.kwin.sell.common.exception.SellException;
import com.kwin.sell.sell.dto.CartDTO;
import com.kwin.sell.sell.model.ProductInfo;
import com.kwin.sell.sell.repository.ProductInfoRepository;
import com.kwin.sell.sell.service.ProductService;

/**
 * 产品信息service实现
 * @author Kwin
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductInfoRepository repository;
	@Override
	@Cacheable(cacheNames = "product", key = "#productId")
	public ProductInfo findOne(String productId) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId(productId);
		Example<ProductInfo> example =Example.of(productInfo);
		return repository.findOne(example).get();
		//return repository.findById(productId).get();
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
//	@CachePut(cacheNames = "product", key = "#productInfo.productId", condition = "#productInfo.productId.length() > 3")//spel表达式   condition 条件成立才缓存
	@CachePut(cacheNames = "product", key = "#productInfo.productId")//spel表达式
	public ProductInfo save(ProductInfo productInfo) {
		return repository.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
			productInfo.setProductStock(result);
			repository.save(productInfo);
		}
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
			if (result < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo.setProductStock(result);
			repository.save(productInfo);
		}
	}
	
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

}
