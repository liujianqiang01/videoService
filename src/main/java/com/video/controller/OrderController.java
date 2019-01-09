package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.video.enumUtil.EnumUtil;
import com.video.model.TOrder;
import com.video.service.OrderService;
import com.video.enumUtil.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一下单接口
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@PostMapping("/subOrder")
	@ResponseBody
	public ApiResponse subOrder(HttpServletRequest requset){
		String vipType = requset.getParameter("vipType");
		if(StringUtils.isEmpty(vipType)){
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		return orderService.subOrder(Integer.valueOf(vipType));
	}
	@PostMapping("/getOrder")
	@ResponseBody
	public ApiResponse getOrder(HttpServletRequest requset){
		TokenBean token = TokenUtil.getToken();
		if(token == null){
			return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
		}
		String pageNum = requset.getParameter("pageNum");
		String pageSize =  requset.getParameter("pageSize");
		if(StringUtils.isEmpty(pageNum)){
			pageNum = "0";
		}
		if(StringUtils.isEmpty(pageSize)){
			pageSize = "5";
		}
		PageInfo<TOrder> order = orderService.getOrder(token.getOpenId(),token.getUserType(),Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		for(TOrder o : order.getList()){
			o.setVipStartDate(DateUtils.formatDate(o.getVipStartTime(),"yyyy-MM-dd"));
			o.setVipEndDate(DateUtils.formatDate(o.getVipEndTime(),"yyyy-MM-dd"));
			//未支付成功的订单不展示vip编码
			if(o.getOrderState() == 1){
				o.setVipCode("");
			}
		}
		return ApiResponse.success(order);
	}

	@PostMapping("/getEarnings")
	@ResponseBody
	public ApiResponse getEarnings(){
		TokenBean token = TokenUtil.getToken();
		if(token == null){
			return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
		}
		if(token.getUserType().equals(EnumUtil.COMMION_USER_TYPE.getCode())){
			return ApiResponse.fail(ApiEnum.NOT_MERCHANT);
		}
		if(StringUtils.isEmpty(token.getMerchantId())){
			return ApiResponse.fail(ApiEnum.PARAM_ERROR);
		}
		return ApiResponse.success(orderService.getEarnings(token));
	}
}
