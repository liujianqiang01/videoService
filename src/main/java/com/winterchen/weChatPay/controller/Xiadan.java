package com.winterchen.weChatPay.controller;


import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.winterchen.weChatPay.common.Configure;
import com.winterchen.weChatPay.common.HttpRequest;
import com.winterchen.weChatPay.common.RandomStringGenerator;
import com.winterchen.weChatPay.common.Signature;
import com.winterchen.weChatPay.model.OrderInfo;
import com.winterchen.weChatPay.model.OrderReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一下单接口
 */
@Controller
public class Xiadan{
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(GetOpenIdController.class);

	@PostMapping("/xiadan")
	@ResponseBody
	public Object xiadan(HttpServletRequest requset){
		JSONObject json = new JSONObject();
		try {
			String openid = requset.getParameter("openid");
			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("dfdfdf");
			order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
			order.setTotal_fee(10);
			order.setSpbill_create_ip("123.57.218.54");
			order.setNotify_url("https://www.see-source.com/weixinpay/PayResult");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);


			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			System.out.println(result);
			log.info("---------下单返回:" + result);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class);
			OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
			json.put("prepay_id", returnInfo.getPrepay_id());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("-------", e);
		}
		return json.toJSONString();
	}

	@PostMapping("/getFree")
	@ResponseBody
	public Object free(HttpServletRequest requset){

		return "adfasdf";
	}
}
