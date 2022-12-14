package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.entity.User;
import com.shen.experiment_store.service.IUserService;
import com.shen.experiment_store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户注册登录使用的控制器
 *
 * @author Shen
 * @date 2022/12/13 19:38
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    IUserService userService;

    /**
     * 注册请求
     *
     * @param user 页面传递来的表单数据封装成的User实体对象
     * @return JSON格式的统一返回结果
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        // 创建统一返回结果类对象，只需要返回状态码和执行信息即可
        JsonResult<Void> result = new JsonResult<>();
        try {
            // 调用业务对象执行注册
            userService.reg(user);
            // 响应成功
            result.setCode(200);
        } catch (UsernameDuplicateException e) {
            // 用户名被占用
            result.setCode(4000);
            result.setMessage("用户名已经被占用");
        } catch (InsertException e) {
            // 插入数据异常
            result.setCode(5000);
            result.setMessage("注册失败，请联系系统管理员");
        }
        return result;
    }

    /**
     * 登录请求
     * 登录成功后将uid和username存入到HttpSession对象中
     *
     * @param username 用户名
     * @param password 密码
     * @param session HttpSession
     * @return 统一返回结果
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
         System.out.println("Session中的uid=" + getUidFromSession(session));
         System.out.println("Session中的username=" + getUsernameFromSession(session));
        return new JsonResult<>(OK, user);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @param session HttpSession
     * @return 统一返回结果
     */
    @RequestMapping("change_password")
    public JsonResult<Void> changePassWord(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    /**
     * 通过UID查询用户信息
     *
     * @param session HttpSession
     * @return 统一返回结果
     */
    @GetMapping("get_by_uid")
    public JsonResult<User> getById(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User user = userService.getByUid(uid);
        return new JsonResult<>(OK, user);
    }

    /**
     * 修改信息
     *
     * @param user 用户信息
     * @param session HttpSession
     * @return 统一返回结果
     */
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /**
     * 头像文件大小的上限值(10MB)
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 允许上传的头像的文件类型
     */
    public static final List<String> AVATAR_TYPES = new ArrayList<>();

    /* 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
        if (file.isEmpty()) {
            throw new FileEmptyException("上传的头像文件不允许为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }
        String contentType = file.getContentType();
        if (!AVATAR_TYPES.contains(contentType)) {
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }
        String parent = session.getServletContext().getRealPath("upload");
        File dir = new File(parent);
        System.out.println("**********" + dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        File dest = new File(dir, filename);

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            throw new FileUploadIOException("上传文件时读写错误，请稍后重尝试");
        }

        String avatar = "/upload/" + filename;
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeAvatar(uid, username, avatar);
        return new JsonResult<>(OK,avatar);
    }
}
