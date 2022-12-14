package com.shen.experiment_store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Shen
 * @date 2022/12/14 17:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}
