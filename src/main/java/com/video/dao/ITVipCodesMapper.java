package com.video.dao;

import com.video.model.TVipCodes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITVipCodesMapper {
    /**
    *批量新增
    */
    int insertBatch(List<TVipCodes> record);

    /**
    *根据主键Id删除
    */
    int deleteByPrimaryKey(Integer id);

    /**
    *根据主键Id查询
    */
    TVipCodes selectByPrimaryKey(Integer id);

    /**
    *根据主键Id修改
    */
    int updateByPrimaryKeySelective(TVipCodes record);

    /**
    *条件查询，返回集合
    */
    List<TVipCodes> selectListByWhere(TVipCodes record);

    /**
    *条件查询
    */
    TVipCodes selectByWhere(TVipCodes record);

    TVipCodes selectOneByWhere(@Param("vipType") Integer vipType, @Param("vipState")Integer vipState, @Param("limit")int limit,@Param("merchantId")String merchantId);
}