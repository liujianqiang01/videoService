package com.video.service;


import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-20
 * @Description:
 */
public interface WholesalePriceService {

    Map<String,Object> getCountPrice(String[] numbers);
}