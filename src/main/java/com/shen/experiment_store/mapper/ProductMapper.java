package com.shen.experiment_store.mapper;

import com.shen.experiment_store.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 16:08
 */
@Mapper
@Repository
public interface ProductMapper {
    /**
     * 查询热销商品的前四名
     *
     * @return 热销商品前四名集合
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品的id
     * @return 匹配的商品详情
     */
    Product findById(Integer id);

    /**
     * 根据商品标题查询商品
     *
     * @param title 商品标题
     * @return 与该标题相关的商品集合
     */
    List<Product> getByTitle(String title);
}
