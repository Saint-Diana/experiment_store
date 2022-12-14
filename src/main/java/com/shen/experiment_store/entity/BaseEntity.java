package com.shen.experiment_store.entity;

import lombok.Data;

import java.util.Date;

/**
 * 基本实体类，将实体类的共有属性提取出来
 *      主要是创建者、创建日期、最后修改者、最后修改日期这四个属性
 *      使用lombok自动生成Setter和Getter方法
 *
 * @author Shen
 * @date 2022/12/13 18:52
 */
@Data
public class BaseEntity {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
