package com.video.dao;

import com.video.model.TMerchantPrice;
import java.util.List;

public interface ITMerchantPriceMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TMerchantPrice> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TMerchantPrice selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TMerchantPrice record);

    /**
    *根据类查询类集合
    */
    List<TMerchantPrice> selectListByClassElement(TMerchantPrice record);

    /**
    *根据类查询类集合
    */
    TMerchantPrice selectByClassElement(TMerchantPrice record);

    TMerchantPrice selectByClassElement();
}