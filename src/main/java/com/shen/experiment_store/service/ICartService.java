package com.shen.experiment_store.service;

import com.shen.experiment_store.vo.CartVO;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 17:12
 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     *
     * @param uid      用户的id
     * @param pid      商品的id
     * @param amount   添加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户购物车数据
     *
     * @param uid 用户的id
     * @return 该用户的购物车列表
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 将购物车中某商品的数量+1
     *
     * @param cid      购物车数量的id
     * @param uid      当前登录用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情数据
     *
     * @param uid  当前登录的用户的id
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
