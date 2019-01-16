package com.video.dao;

import com.video.model.TSettleAccount;

import java.math.BigDecimal;
import java.util.List;

public interface ITSettleAccountMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TSettleAccount> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TSettleAccount selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TSettleAccount record);

    /**
    *根据类查询类集合
    */
    List<TSettleAccount> selectListByClassElement(TSettleAccount record);

    /**
    *根据类查询类集合
    */
    TSettleAccount selectByClassElement(TSettleAccount record);
    /**
     * 获取未收益
     * @return
     */
    BigDecimal getEarnings(String merchantId);
}