package com.video.controller;


import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.model.Ao.OrderInfo;
import com.video.model.Ao.OrderReturnInfo;
import com.video.util.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@PostMapping("/subOrder")
	@ResponseBody
	public ApiResponse subOrder(HttpServletRequest requset){
		JSONObject json = new JSONObject();
		try {
			TokenBean token = TokenUtil.getToken();
			if(token == null){
				log.info("---------下单 token : " + token);
				return ApiResponse.fail(ApiEnum.PARAM_NULL);
			}
			String openid = token.getOpenId();
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("影视会员VIP年卡");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setTotal_fee(BigDecimal.valueOf(0.01));
			order.setSpbill_create_ip("47.104.96.127");
			order.setNotify_url("https://www.videoalliance.cn/PayResult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);


			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			log.info("---------下单返回:" + result);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class);
			OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
			if(!"SUCCESS".equals(returnInfo.getReturn_code())){
				return ApiResponse.fail(ApiEnum.PARAM_ERROR);
			}
			json.put("prepayId", returnInfo.getPrepay_id());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("-------", e);
		}
		return ApiResponse.success(json.toJSONString());
	}

	@PostMapping("/getFree")
	@ResponseBody
	public Object free(HttpServletRequest requset){

		return "adfasdf";
	}
}
