package com.video.controller;

import com.video.enumUtil.ApiEnum;
import com.video.service.WholesaleOrderService;
import com.video.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-21
 * @Description:
 */
@Controller
@RequestMapping("/wholesaleOrder")
public class WholesaleOrderController {
    @Autowired
    WholesaleOrderService wholesaleOrderService;
    @PostMapping("/subOrder")
    @ResponseBody
    public ApiResponse subOrder(String[] number, BigDecimal totalPrice){
        if(number == null || number.length <= 0 || totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) <= 0){
            return ApiResponse.fail(ApiEnum.PARAM_NULL);
        }
        return ApiResponse.success(wholesaleOrderService.subOrder(number,totalPrice));
    }
}