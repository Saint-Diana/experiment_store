package com.shen.experiment_store.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * @author Shen
 * @date 2022/12/14 10:44
 */
@Data
public class District implements Serializable {
    private Integer id;
    private String parent;
    private String code;
    private String name;
}
