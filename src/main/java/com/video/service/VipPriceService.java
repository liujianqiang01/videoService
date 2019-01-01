package com.video.service;

import com.video.model.TVipPrice;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface VipPriceService {
    /**
     *批量新增
     */
    int insertBatch(List<TVipPrice> record);

    /**
     *根据主键Id删除
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *根据主键Id查询
     */
    TVipPrice selectByPrimaryKey(Integer id);

    /**
     *根据主键Id修改
     */
    int updateByPrimaryKeySelective(TVipPrice record);

    /**
     *条件查询，返回集合
     */
    List<TVipPrice> selectListByWhere(TVipPrice record);

    /**
     *条件查询
     */
    TVipPrice selectByWhere(TVipPrice record);
}