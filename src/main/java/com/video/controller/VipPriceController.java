package com.video.controller;

import com.video.model.TVipPrice;
import com.video.service.VipPriceService;
import com.video.service.WholesalePriceService;
import com.video.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    WholesalePriceService wholesalePriceService;

    @PostMapping("/getVipType")
    @ResponseBody
    public ApiResponse getVipType(){
        TVipPrice vipPrice = new TVipPrice();
        List<TVipPrice> tVipPrices = vipPriceService.selectListByWhere(vipPrice);
        return ApiResponse.success(tVipPrices);
    }

    @PostMapping("/getBanner")
    @ResponseBody
    public ApiResponse getBanner(){
        List<String> banners = new ArrayList<>();
        banners.add("https://filmunion.com.cn/video/image/banner1.jpeg");
        banners.add("https://filmunion.com.cn/video/image/banner2.jpeg");
        banners.add("https://filmunion.com.cn/video/image/banner3.jpeg");
        return ApiResponse.success(banners);
    }

    @PostMapping("/getMVipType")
    @ResponseBody
    public ApiResponse getMVipType(){
        TVipPrice vipPrice = new TVipPrice();
        List<TVipPrice> tVipPrices = vipPriceService.selectListByWhere(vipPrice);
        Map<String,Object> result = new HashMap<>();
        result.put("tVipPrices",tVipPrices);
        wholesalePriceService.countOrder(result);
        return ApiResponse.success(result);
    }
}