package com.video.service;

import com.video.model.TUser;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface UserService {
    /**
     *批量新增
     */
    int insertBatch(List<TUser> record);

    /**
     *根据主键Id删除
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     *根据主键Id查询
     */
    TUser selectByPrimaryKey(Integer userId);

    /**
     *根据主键Id修改
     */
    int updateByPrimaryKeySelective(TUser record);

    /**
     *条件查询，返回集合
     */
    List<TUser> selectListByWhere(TUser record);

    /**
     *条件查询
     */
    TUser selectByWhere(TUser record);
}