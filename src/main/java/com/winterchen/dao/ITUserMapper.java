package com.winterchen.dao;

import com.winterchen.model.TUser;
import java.util.List;

public interface ITUserMapper {
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