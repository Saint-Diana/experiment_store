package com.shen.experiment_store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shen
 * @date 2022/12/14 16:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Product extends BaseEntity{
    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;
}
