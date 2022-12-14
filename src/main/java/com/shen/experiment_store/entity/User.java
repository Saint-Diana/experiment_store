package com.shen.experiment_store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户类，用于标识系统中进行操作的实体
 *
 * @author Shen
 * @date 2022/12/13 18:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
}
