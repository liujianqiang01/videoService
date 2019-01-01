package com.video.service;

import com.video.model.TOrder;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface OrderService {
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
}
