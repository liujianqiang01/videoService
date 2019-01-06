package com.video.service;

import com.video.model.Ao.PayResultInfo;
import com.video.model.TOrder;
import com.video.util.ApiResponse;

import java.util.List;

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
    List<TOrder> getOrder(String openId, Integer userType);

    /**
     * 支付成功
     * @param order
     */
    void successOrder(PayResultInfo order);

    /**
     * 支付失败
     * @param order
     */
    void unSuccessOrder(PayResultInfo order);

    /**
     * 订单超时检查
     */
    void unPayTask();
}
