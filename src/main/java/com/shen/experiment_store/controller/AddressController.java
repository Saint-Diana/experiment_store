package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.Address;
import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 10:47
 */
@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getByUid(uid);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        addressService.setDefault(aid, uid, username);
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.delete(aid, uid, username);
        return new JsonResult<>(OK);
    }
}
