package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.service.ICartService;
import com.shen.experiment_store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 17:13
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    @PostMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        //从session中获取用户的id和用户的名称
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用添加到购物车的方法
        cartService.addToCart(uid, pid, amount, username);
        return new JsonResult<>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        //从session中获取uid
        Integer uid = getUidFromSession(session);
        //查询购物车信息
        List<CartVO> list = cartService.getVOByUid(uid);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        //从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用添加到购物车的方法
        Integer num = cartService.addNum(cid, uid, username);
        return new JsonResult<>(OK, num);
    }

    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);
        // 调用业务对象执行查询数据
        List<CartVO> data = cartService.getVOByCids(uid, cids);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }
}
