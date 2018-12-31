package com.video.controller;


import com.video.common.Configure;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class GetOpenIdController {
	private static Logger log = LoggerFactory.getLogger(GetOpenIdController.class);

	@PostMapping("/getOpenId")
	@ResponseBody
	public Object getOpenId(@RequestBody String code) throws  IOException{
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+ Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
		//设置请求器的配置
		HttpClient httpClient = HttpClients.createDefault();
		HttpResponse res = httpClient.execute(httpGet);
		HttpEntity entity = res.getEntity();
		String result = EntityUtils.toString(entity, "UTF-8");
		return  result;
	}

}
