package com.shen.experiment_store.service.impl;

import com.shen.experiment_store.entity.Product;
import com.shen.experiment_store.mapper.ProductMapper;
import com.shen.experiment_store.service.IProductService;
import com.shen.experiment_store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 16:12
 */
@Service
public class IProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> hotList = productMapper.findHotList();
        for (Product product : hotList) {
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setModifiedTime(null);
            product.setCreatedUser(null);
            product.setModifiedUser(null);
        }
        return hotList;
    }

    @Override
    public Product findById(Integer id) {
        //根据参数id查询商品信息
        Product product = productMapper.findById(id);
        //判断查询结果是否为null
        if (product == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        //将查询结果中的部分数据显示为null
        product.setModifiedUser(null);
        product.setCreatedUser(null);
        product.setModifiedTime(null);
        product.setCreatedTime(null);
        product.setPriority(null);
        return product;
    }

    @Override
    public List<Product> getByTitle(String title) {
        List<Product> products = productMapper.getByTitle(title);
        if(products == null){
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        return products;
    }
}
