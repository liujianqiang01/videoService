package com.video.controller;

import com.video.model.TVipPrice;
import com.video.service.VipPriceService;
import com.video.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-05
 * @Description:
 */
@Controller
public class VipPriceController {
    private static Logger log = LoggerFactory.getLogger(VipPriceController.class);

    @Autowired
    private VipPriceService vipPriceService;

    @PostMapping("/getVipType")
    @ResponseBody
    public ApiResponse getVipType(){
        TVipPrice vipPrice = new TVipPrice();
        List<TVipPrice> tVipPrices = vipPriceService.selectListByWhere(vipPrice);
        return ApiResponse.success(tVipPrices);
    }

}