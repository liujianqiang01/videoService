package com.video.service;


import com.video.model.ao.PayResultInfo;
import com.video.util.ApiResponse;

import java.math.BigDecimal;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-21
 * @Description:
 */
public interface WholesaleOrderService {
    ApiResponse subOrder(String[] number, BigDecimal totalPrice);
    void unSuccessOrder(PayResultInfo order);
    boolean successOrder(PayResultInfo param);

}