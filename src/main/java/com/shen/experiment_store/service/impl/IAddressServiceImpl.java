package com.shen.experiment_store.service.impl;

import com.shen.experiment_store.entity.Address;
import com.shen.experiment_store.mapper.AddressMapper;
import com.shen.experiment_store.service.IAddressService;
import com.shen.experiment_store.service.IDistrictService;
import com.shen.experiment_store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Shen
 * @date 2022/12/14 10:39
 */
@Service
public class IAddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Value("${user.address.max-count}")
    private int maxCount;

    @Autowired
    private IDistrictService districtService;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer addCount = addressMapper.countByUid(uid);
        if (addCount > maxCount) {
            throw new AddressCountLimitException("收货地址数量已经达到上限(" + maxCount + ")!");
        }
        address.setUid(uid);
        Integer isDefault = addCount == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        Date date = new Date();

        // 补全数据：省、市、区的名称
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setCreatedTime(date);
        address.setModifiedTime(date);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入收货地址数据时出现未知错误，请联系系统管理员！");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问的异常");
        }
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1) {
            throw new UpdateException("设置默认收货地址时出现未知错误[1]");
        }
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("设置默认收货地址时出现未知错误[2]");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非常访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除收货地址数据时出现未知错误，请联系系统管理员");
        }
        Address lastModified = addressMapper.findLastModified(uid);
        Integer lastModifiedAid = lastModified.getAid();
        Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        if (rows2 != 1) {
            throw new UpdateException("更新收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        //根据收获地址id,查询收获地址详情
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }
}
