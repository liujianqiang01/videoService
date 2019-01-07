package com.video.service;

import com.github.pagehelper.PageInfo;
import com.video.model.Ao.PayResultInfo;
import com.video.model.TOrder;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;

import java.math.BigDecimal;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface OrderService {
    /**
     * 提交订单
     * @param vipType
     * @return
     */
    ApiResponse subOrder(Integer vipType);

    /**
     * 获取用户订单
     * @param openId
     * @param userType
     * @return
     */
    PageInfo<TOrder> getOrder(String openId, Integer userType,int pageNum,int pageSize);

    /**
     * 支付成功
     * @param order
     */
    boolean successOrder(PayResultInfo order);

    /**
     * 支付失败
     * @param order
     */
    void unSuccessOrder(PayResultInfo order);

    /**
     * 订单超时检查
     */
    void unPayTask();

    /**
     * 同步微信订单
     */
    void syncWeixinOrder();

    /**
     * 获取收益
     * @return
     */
    BigDecimal getEarnings(TokenBean token);
}
