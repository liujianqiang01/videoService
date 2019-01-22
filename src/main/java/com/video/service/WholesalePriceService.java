package com.video.service;


import com.video.model.TWholesaleOrder;

import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-20
 * @Description:
 */
public interface WholesalePriceService {

    Map<String,Object> getCountPrice(String[] numbers);

    void handOut(TWholesaleOrder order);

    void countOrder( Map<String,Object> countPrice);
}