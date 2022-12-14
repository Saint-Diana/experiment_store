package com.shen.experiment_store.service.impl;

import com.shen.experiment_store.entity.User;
import com.shen.experiment_store.mapper.UserMapper;
import com.shen.experiment_store.service.IUserService;
import com.shen.experiment_store.service.ex.PasswordNotMatchException;
import com.shen.experiment_store.service.ex.UpdateException;
import com.shen.experiment_store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Service实现类
 *
 * @author Shen
 * @date 2022/12/13 19:13
 */
@Service
public class IUserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        //首先判断数据库中是否已经有该用户名，若已经存在，则提示该用户名不可用！
        if (result != null) {
            throw new RuntimeException("注册的用户名" + username + "已经被占用了");
        }
        //否则就将表单传递来的用户数据插入到数据库中
        Date now = new Date();
        //随机生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //通过加密函数进行加密，数据库保存加密后的密码
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new RuntimeException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 执行密码加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    public String getMd5Password(String password, String salt) {
        //加密3次
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        //首先查找数据库，判断是否存在该用户名的用户，若不存在，则抛出异常！
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在的错误");
        }
        //然后查看该用户是否有效，若该用户已经弃用，则同样提示用户不存在
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在的错误");
        }
        //获取盐，用于解密
        String salt = result.getSalt();
        String md5Password = getMd5Password(password, salt);
        //如果传送过来的密码加密后不等于数据库中存放的密文，则说明密码错误
        if (!result.getPassword().equals(md5Password)) {
            throw new PasswordNotMatchException("密码验证失败的错误");
        }
        //一切都正确后，方可登录
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        //修改用户密码
        //同样的先去数据库中查看是否存在该用户名的用户
        User result = userMapper.findByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //然后获取盐值，用于验证输入的原密码是否正确
        String salt = result.getSalt();
        String oldMd5Password = getMd5Password(oldPassword, salt);
        if (!result.getPassword().contentEquals(oldMd5Password)) {
            throw new PasswordNotMatchException("原密码错误");
        }
        //一切正确后，将新密码加密后存放到数据库中，覆盖原先的密码
        String md5Password = getMd5Password(newPassword, salt);
        Date date = new Date();
        Integer rows = userMapper.updatePasswordByUid(uid, md5Password, username, date);
        if (rows != 1) {
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        //通过uid查找用户信息
        //首先判断是否存在该uid的用户
        User result = userMapper.findByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //若存在，则将其信息返回
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        user.setEmail(result.getEmail());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        //修改用户信息
        User result = userMapper.findByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新用户数据时出错误了,请联系管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        User result = userMapper.findByUid(uid);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }

        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }

        Date now = new Date();
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        if (rows != 1) {
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }
}
