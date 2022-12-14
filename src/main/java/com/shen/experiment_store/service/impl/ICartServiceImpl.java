package com.shen.experiment_store.service.impl;

import com.shen.experiment_store.entity.Cart;
import com.shen.experiment_store.entity.Product;
import com.shen.experiment_store.mapper.CartMapper;
import com.shen.experiment_store.service.ICartService;
import com.shen.experiment_store.service.IProductService;
import com.shen.experiment_store.service.ex.AccessDeniedException;
import com.shen.experiment_store.service.ex.CartNotFoundException;
import com.shen.experiment_store.service.ex.InsertException;
import com.shen.experiment_store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 17:12
 */
@Service
public class ICartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private IProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        //根据pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date now = new Date();
        //判断查询的结果是否为null
        if (result == null) {
            //如果是null表示改用户没有将商品加入到购物车
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //根据商品的id查询商品信息得到商品价格
            Product product = productService.findById(pid);
            //封装数据
            cart.setPrice(product.getPrice());
            cart.setCreatedTime(now);
            cart.setModifiedTime(now);
            cart.setCreatedUser(username);
            cart.setModifiedUser(username);
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        } else {
            //如果不是null 获取购物车数据的id
            Integer cid = result.getCid();
            //查询原数据和参数amount相加,得到新的数量
            Integer num = result.getNum() + amount;
            //执行更新操作
            Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
            if (rows != 1) {
                throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        //调用findByCid根据cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() + 1;

        //创建当前时间对象,作为modifiedTime
        Date now = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new RuntimeException("修改商品数量时出现未知错误，请联系系统管理员");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        list.removeIf(cartVO -> !cartVO.getUid().equals(uid));
        return list;
    }
}
