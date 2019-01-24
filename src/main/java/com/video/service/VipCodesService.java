package com.video.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.video.model.TOrder;
import com.video.model.TVipCodes;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface VipCodesService {
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

    PageInfo<TVipCodes> findPage(Integer pageNum, Integer pageSize ,TVipCodes vipCodes );

}
