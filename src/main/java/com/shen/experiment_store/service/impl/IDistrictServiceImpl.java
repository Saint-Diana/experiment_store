package com.shen.experiment_store.service.impl;

import com.shen.experiment_store.entity.District;
import com.shen.experiment_store.mapper.DistrictMapper;
import com.shen.experiment_store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 10:41
 */
@Service
public class IDistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
