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
    ApiResponse subOrder(Integer vipType);
    List<TOrder> getOrder(String openId, Integer userType);
    void successOrder(PayResultInfo order);
    void unSuccessOrder(PayResultInfo order);
}
