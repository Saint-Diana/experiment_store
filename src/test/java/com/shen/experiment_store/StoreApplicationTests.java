package com.shen.experiment_store;

import com.shen.experiment_store.entity.User;
import com.shen.experiment_store.mapper.UserMapper;
import com.shen.experiment_store.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class StoreApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() {
        User user = new User();
        user.setUsername("zangsan");
        user.setEmail("1439575437@qq.com");
        user.setGender(1);
        user.setPhone("123421231");
        user.setPassword("123455");
        userMapper.insert(user);
    }

    @Autowired
    private IUserService iUserService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("lower");
            user.setPassword("123456");
            user.setGender(1);
            user.setPhone("17858802974");
            user.setEmail("lower@tedu.cn");
            user.setAvatar("xxxx");
            iUserService.reg(user);
            System.out.println("注册成功！");
        } catch (Exception e) {
            System.out.println("注册失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void updatePasswordByUid() {
        Integer uid = 1;
        String password = "123456";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updatePasswordByUid(uid, password, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }


    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(1);
        user.setPhone("17858802222");
        user.setEmail("admin@cy.com");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows=" + rows);
    }

}
