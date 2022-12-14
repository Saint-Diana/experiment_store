package com.shen.experiment_store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户地址类，记录了用户的地址信息
 *
 * @author Shen
 * @date 2022/12/14 10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends BaseEntity implements Serializable {
    /**
     * 收货地址id
     */
    private Integer aid;
    /**
     * 归属的用户id
     */
    private Integer uid;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 省-名称
     */
    private String provinceName;
    /**
     * 省-行政代号
     */
    private String provinceCode;
    /**
     * 市-名称
     */
    private String cityName;
    /**
     * 市-行政代号
     */
    private String cityCode;
    /**
     * 区-名称
     */
    private String areaName;
    /**
     * 区-行政代号
     */
    private String areaCode;
    /**
     * 邮政编码
     */
    private String zip;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 手机
     */
    private String phone;
    /**
     * 固话
     */
    private String tel;
    /**
     * 标签
     */
    private String tag;
    /**
     * 是否默认：0-不默认，1-默认
     */
    private Integer isDefault;
}
