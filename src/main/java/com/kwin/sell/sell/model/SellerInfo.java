package com.kwin.sell.sell.model;

import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 卖家信息表
 * @author Kwin
 *
 */
@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
    
    private Date createTime;

    private Date updateTime;
}
