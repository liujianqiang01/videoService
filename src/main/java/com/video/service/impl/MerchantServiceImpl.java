package com.video.service.impl;

import com.video.dao.ITMerchantMapper;
import com.video.model.TMerchant;
import com.video.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    ITMerchantMapper merchantMapper;
    @Override
    public int insertBatch(List<TMerchant> record) {
        return merchantMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return merchantMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TMerchant selectByPrimaryKey(Integer id) {
        return merchantMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TMerchant record) {
        return merchantMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TMerchant> selectListByWhere(TMerchant record) {
        return merchantMapper.selectListByWhere(record);
    }

    @Override
    public TMerchant selectByWhere(TMerchant record) {
        return merchantMapper.selectByWhere(record);
    }
}