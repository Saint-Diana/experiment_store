package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.entity.Product;
import com.shen.experiment_store.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 16:13
 */
@RestController
@RequestMapping("products")
public class ProductController extends BaseController{
    @Autowired
    private IProductService productService;

    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> hotList = productService.findHotList();
        return new JsonResult<>(OK, hotList);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        return new JsonResult<>(OK, product);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<Product>> getByTitle(String title) {
        //首先将title转为模糊搜索使用的title
        String searchTitle = "%" + title + "%";
        List<Product> list = productService.getByTitle(searchTitle);
        return new JsonResult<>(OK, list);
    }
}
