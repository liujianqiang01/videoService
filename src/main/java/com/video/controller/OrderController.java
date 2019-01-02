package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.video.service.OrderService;
import com.video.util.ApiEnum;
import com.video.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一下单接口
 */
@Controller
public class OrderController {
	private static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@PostMapping("/subOrder")
	@ResponseBody
	public ApiResponse subOrder(HttpServletRequest requset){
		String spbill_create_ip = requset.getParameter("spbill_create_ip");
		String vipType = requset.getParameter("vipType");
		if(StringUtils.isEmpty(spbill_create_ip)){
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		if(StringUtils.isEmpty(vipType)){
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		return orderService.subOrder(spbill_create_ip,Integer.valueOf(vipType));
	}

	@PostMapping("/getFree")
	@ResponseBody
	public Object free(HttpServletRequest requset){

		return "adfasdf";
	}
}
