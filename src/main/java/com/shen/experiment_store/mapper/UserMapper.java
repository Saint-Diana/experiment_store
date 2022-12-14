package com.shen.experiment_store.mapper;

import com.shen.experiment_store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用于处理用户数据操作的持久层接口
 *
 * @author Shen
 * @date 2022/12/13 19:02
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user 用户
     * @return 操作影响的行数，若返回0，说明操作失败；返回1，说明操作成功
     */
    public Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    public User findByUsername(String username);

    /**
     * 根据uid更新用户的密码
     *
     * @param uid          用户的id
     * @param password     新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid, @Param("password") String password, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     *
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据uid更新用户资料
     *
     * @param user 封装了用户id和新个人资料的对象
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid更新用户的头像
     *
     * @param uid          用户的id
     * @param avatar       新头像的路径
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,@Param("avatar") String avatar,@Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);
}
