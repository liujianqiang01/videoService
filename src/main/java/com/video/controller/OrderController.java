package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.model.Ao.OrderInfo;
import com.video.model.Ao.OrderReturnInfo;
import com.video.service.OrderService;
import com.video.util.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.tools.jstat.Token;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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
