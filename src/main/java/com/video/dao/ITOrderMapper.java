package com.video.dao;

import com.video.model.TOrder;
import com.video.util.TokenBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ITOrderMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TOrder> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TOrder selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TOrder record);

    /**
    *条件查询，返回集合
    */
    List<TOrder> selectListByWhere(TOrder record);

    /**
    *条件查询
    */
    TOrder selectByWhere(TOrder record);

    /**
     * 查询超时订单
     * @return
     */
    List<TOrder> getTimeOutOrder(Date date);
    /**
     * 获取未收益
     * @return
     */
    BigDecimal getUnEarnings(String merchantId);

    /**
     *连接条件查询，返回集合
     */
    List<TOrder> selectJojnListByWhere(TOrder record);

    /**
     *统计商户订单
     */
    BigDecimal countByMerchant(TOrder record);
}