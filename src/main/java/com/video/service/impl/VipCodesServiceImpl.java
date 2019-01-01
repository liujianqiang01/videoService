package com.video.service.impl;

import com.video.dao.ITVipCodesMapper;
import com.video.model.TVipCodes;
import com.video.service.VipCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class VipCodesServiceImpl implements VipCodesService {

    @Autowired
    ITVipCodesMapper vipCodesMapper;
    @Override
    public int insertBatch(List<TVipCodes> record) {
        return vipCodesMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return vipCodesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TVipCodes selectByPrimaryKey(Integer id) {
        return vipCodesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TVipCodes record) {
        return vipCodesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TVipCodes> selectListByWhere(TVipCodes record) {
        return vipCodesMapper.selectListByWhere(record);
    }

    @Override
    public TVipCodes selectByWhere(TVipCodes record) {
        return vipCodesMapper.selectByWhere(record);
    }
}