package com.video.service.impl;

import com.video.dao.ITOrderMapper;
import com.video.model.TOrder;
import com.video.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ITOrderMapper orderMapper;
    @Override
    public int insertBatch(List<TOrder> record) {
        return orderMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TOrder selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TOrder record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TOrder> selectListByWhere(TOrder record) {
        return orderMapper.selectListByWhere(record);
    }

    @Override
    public TOrder selectByWhere(TOrder record) {
        return orderMapper.selectByWhere(record);
    }
}