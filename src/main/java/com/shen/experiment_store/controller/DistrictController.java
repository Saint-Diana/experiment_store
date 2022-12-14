package com.shen.experiment_store.controller;

import com.shen.experiment_store.entity.District;
import com.shen.experiment_store.entity.JsonResult;
import com.shen.experiment_store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 15:14
 */
@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;

    @GetMapping({"", "/"})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> list = districtService.getByParent(parent);
        return new JsonResult<>(OK, list);
    }
}
