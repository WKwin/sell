package com.kwin.sell.sell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kwin.sell.common.enums.ProductStatusEnum;
import com.kwin.sell.common.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * product产品信息
 * @author Kwin
 *
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	@Id
    private String productId;//不需要自增注解，自己产生随机字符

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
