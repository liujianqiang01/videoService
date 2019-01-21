package com.video.service.impl;

import com.video.dao.ITVipPriceMapper;
import com.video.model.TVipPrice;
import com.video.service.VipPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class VipPriceServiceImpl implements VipPriceService {
    @Autowired
    private ITVipPriceMapper vipPriceMapper;
    @Override
    public int insertBatch(List<TVipPrice> record) {
        return vipPriceMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return vipPriceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TVipPrice selectByPrimaryKey(Integer id) {
        return vipPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TVipPrice record) {
        return vipPriceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TVipPrice> selectListByWhere(TVipPrice record) {
        return vipPriceMapper.selectListByWhere(record);
    }

    @Override
    public TVipPrice selectByWhere(TVipPrice record) {
        return vipPriceMapper.selectByWhere(record);
    }
}