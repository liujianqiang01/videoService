package com.video.controller;

import com.video.service.WholesalePriceService;
import com.video.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-20
 * @Description:
 */
@Controller
@RequestMapping("/wholesalePrice")
public class WholesalePriceController {
    private static Logger log = LoggerFactory.getLogger(WholesalePriceController.class);

    @Autowired
    WholesalePriceService wholesalePriceService;
    @PostMapping("/getTotalPrice")
    @ResponseBody
    public ApiResponse getTotalPrice(String[] numbers){
        Map<String,Object> countPrice = wholesalePriceService.getCountPrice(numbers);
        return ApiResponse.success(countPrice);
    }
}