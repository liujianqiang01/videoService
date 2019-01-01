package com.video.service.impl;

import com.video.dao.ITUserMapper;
import com.video.model.TUser;
import com.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ITUserMapper userMapper;
    @Override
    public int insertBatch(List<TUser> record) {
        return userMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public TUser selectByPrimaryKey(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(TUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TUser> selectListByWhere(TUser record) {
        return userMapper.selectListByWhere(record);
    }

    @Override
    public TUser selectByWhere(TUser record) {
        return userMapper.selectByWhere(record);
    }
}