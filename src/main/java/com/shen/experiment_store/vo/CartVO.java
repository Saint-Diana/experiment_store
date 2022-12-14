package com.shen.experiment_store.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shen
 * @date 2022/12/14 17:04
 */
@Data
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
}
