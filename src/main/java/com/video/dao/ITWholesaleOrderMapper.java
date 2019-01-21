package com.video.dao;

import com.video.model.TWholesaleOrder;
import java.util.List;

public interface ITWholesaleOrderMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TWholesaleOrder> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TWholesaleOrder selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TWholesaleOrder record);

    /**
    *根据类查询类集合
    */
    List<TWholesaleOrder> selectListByClassElement(TWholesaleOrder record);

    /**
    *根据类查询类集合
    */
    TWholesaleOrder selectByClassElement(TWholesaleOrder record);
}