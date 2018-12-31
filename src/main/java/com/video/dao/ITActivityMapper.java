package com.video.dao;

import com.video.model.TActivity;
import java.util.List;

public interface ITActivityMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TActivity> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TActivity selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TActivity record);

    /**
    *条件查询，返回集合
    */
    List<TActivity> selectListByWhere(TActivity record);

    /**
    *条件查询
    */
    TActivity selectByWhere(TActivity record);
}