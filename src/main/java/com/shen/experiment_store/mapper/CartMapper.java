package com.shen.experiment_store.mapper;


import com.shen.experiment_store.entity.Cart;
import com.shen.experiment_store.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author Shen
 * @date 2022/12/14 17:04
 */
@Mapper
@Repository
public interface CartMapper {

    /**
     * 插入购物车数据
     *
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 修改购物车数据中商品的数据
     *
     * @param cid          购物车数据的id
     * @param num          新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     *
     * @param uid 用户id
     * @param pid 商品od
     * @return 匹配购物车的数据, 如果该用户的购物车中并没有该商品, 则为null
     */
    Cart findByUidAndPid(@Param("uid") Integer uid, @Param("pid") Integer pid);

    /**
     * 查询某用户的购物车数据
     *
     * @param uid 用户的id
     * @return 该用户的购物车列表
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 根据购物车数据id查询购物车数据详情
     *
     * @param cid 购物车数据id
     * @return 匹配购物车数据详情, 如果没有数据就返回null
     */
    Cart findByCid(Integer cid);

    /**
     * 根据若干个购物车数据id查询详情的列表
     *
     * @param ids 若干个购物车的数据
     * @return 匹配购物车数据详情的数据
     */
    List<CartVO> findVOByCids(Integer[] ids);

}
