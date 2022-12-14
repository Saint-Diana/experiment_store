package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.service.ex.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器基类，提取出控制器中的共用方法
 *
 * @author Shen
 * @date 2022/12/13 19:43
 */
public class BaseController {
    public static final int OK = 200;

    /**
     * 异常处理器方法
     *
     * @param e 抛出的异常类
     * @return 统一返回结果，状态码和信息
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setCode(4000);
        } else if (e instanceof UserNotFoundException) {
            result.setCode(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setCode(4002);
        } else if (e instanceof AddressCountLimitException) {
            result.setCode(4003);
        } else if (e instanceof AddressNotFoundException) {
            result.setCode(4004);
        } else if (e instanceof AccessDeniedException) {
            result.setCode(4005);
        } else if (e instanceof ProductNotFoundException) {
            result.setCode(4006);
        } else if (e instanceof InsertException) {
            result.setCode(5000);
        } else if (e instanceof UpdateException) {
            result.setCode(5001);
        } else if (e instanceof DeleteException) {
            result.setCode(5002);
        } else if (e instanceof FileEmptyException) {
            result.setCode(6000);
        } else if (e instanceof FileSizeException) {
            result.setCode(6001);
        } else if (e instanceof FileTypeException) {
            result.setCode(6002);
        } else if (e instanceof FileStateException) {
            result.setCode(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setCode(6004);
        }
        return result;
    }

    /**
     * 从HttpSession对象中获取uid
     *
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     *
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
