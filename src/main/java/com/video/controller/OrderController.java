package com.video.controller;


import com.alibaba.druid.util.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
		String vipType = requset.getParameter("vipType");
		if(StringUtils.isEmpty(vipType)){
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		return orderService.subOrder(Integer.valueOf(vipType));
	}
	@PostMapping("/getOrder")
	@ResponseBody
	public ApiResponse getOrder(){
		TokenBean token = TokenUtil.getToken();

		List<TOrder> order = orderService.getOrder(token.getOpenId(),token.getUserType());
		for(TOrder o : order){
			o.setVipStartDate(DateUtils.formatDate(o.getVipStartTime(),"yyyy-MM-dd"));
			o.setVipEndDate(DateUtils.formatDate(o.getVipEndTime(),"yyyy-MM-dd"));
			//未支付成功的订单不展示vip编码
			if(o.getVipState() == 0){
				o.setVipCode(null);
			}
		}
		return ApiResponse.success(order);
	}

}
