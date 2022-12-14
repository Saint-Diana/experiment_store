package com.shen.experiment_store.service;

import com.shen.experiment_store.entity.Product;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 16:12
 */
public interface IProductService {
    /**
     * 查询热销商品前四名
     *
     * @return 热销商品前四名的集合
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品的id
     * @return 匹配的商品详情, 如果没有匹配到的数据则返回null
     */
    Product findById(Integer id);

    /**
     * 根据商品title查询商品集合
     *
     * @param title 商品标题
     * @return 匹配的商品集合
     */
    List<Product> getByTitle(String title);
}
