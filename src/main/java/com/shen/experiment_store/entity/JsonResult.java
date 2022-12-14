package com.shen.experiment_store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果R类
 *
 * @author Shen
 * @date 2022/12/13 19:36
 */
@Data
public class JsonResult<E> implements Serializable {
    /** 状态码 */
    private Integer code;
    /** 状态描述信息 */
    private String message;
    /** 数据 */
    private E data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer code) {
        super();
        this.code = code;
    }

    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer code, E data) {
        super();
        this.code = code;
        this.data = data;
    }
}
