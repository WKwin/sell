package com.kwin.sell.sell.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * Product类目
 * @author Kwin
 * @Table(name = "s_product_category") //表名与驼峰不一致使用
 */
@Entity
@DynamicUpdate //动态更新时间
@Data  //lombok工具，帮助生成setter/getter，toString,代码更优雅，修改更方便，效率没有损失  @Getter  @Setter  @ToString
public class ProductCategory {

    /** 类目id. */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
    
}
