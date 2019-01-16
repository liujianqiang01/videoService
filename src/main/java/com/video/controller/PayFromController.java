package com.video.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.video.common.Configure;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.enumUtil.ApiEnum;
import com.video.model.ao.SignInfo;
import com.video.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 再签名
 */
@Controller
public class PayFromController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@PostMapping("/getPayForm")
	@ResponseBody
	public ApiResponse getPayForm(HttpServletRequest requset) throws IllegalAccessException {
		String prepayId = requset.getParameter("prepayId");
		if(StringUtils.isEmpty(prepayId)){
			return ApiResponse.success(ApiEnum.PARAM_NULL);
		}
		SignInfo signInfo = new SignInfo();
		signInfo.setAppId(Configure.getAppID());
		long time = System.currentTimeMillis()/1000;
		signInfo.setTimeStamp(String.valueOf(time));
		signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
		signInfo.setRepay_id("prepay_id="+prepayId);
		signInfo.setSignType("MD5");
		//生成签名
		String sign = Signature.getSign(signInfo);

		JSONObject json = new JSONObject();
		json.put("timeStamp", signInfo.getTimeStamp());
		json.put("nonceStr", signInfo.getNonceStr());
		json.put("package", signInfo.getRepay_id());
		json.put("signType", signInfo.getSignType());
		json.put("paySign", sign);
		log.info("-------再签名:"+json.toJSONString());
		return  ApiResponse.success(json.toJSONString());
	}
}
