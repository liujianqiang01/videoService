package com.video.dao;

import com.video.model.TWholesalePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITWholesalePriceMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TWholesalePrice> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TWholesalePrice selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TWholesalePrice record);

    /**
    *根据类查询类集合
    */
    List<TWholesalePrice> selectListByClassElement(TWholesalePrice record);

    /**
    *根据类查询类集合
    */
    TWholesalePrice selectByClassElement(TWholesalePrice record);

    /**
     *根据类查和数量询类集合
     */
    List<TWholesalePrice> selectBetween(@Param("vipType") Integer vipType, @Param("number")Integer number);
}