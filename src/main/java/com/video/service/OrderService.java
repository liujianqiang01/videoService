package com.video.service;

import com.video.model.Ao.OrderInfo;
import com.video.util.ApiResponse;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface OrderService {
    ApiResponse subOrder(String spbill_create_ip, Integer vipType);
}
