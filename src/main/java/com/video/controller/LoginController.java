package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.video.common.Configure;
import com.video.model.Ao.HeaderInfo;
import com.video.model.TUser;
import com.video.service.UserService;
import com.video.util.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Controller()
@RequestMapping("/login")
public class LoginController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserService userService;
	@PostMapping("/getToken")
	@ResponseBody
	public ApiResponse getToken(HttpServletRequest request) throws Exception{
		String code = request.getParameter("code");
		log.info("code="+code);
		if(StringUtils.isEmpty(code)){
			log.error("微信登陆code为空！");
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+ Configure.getAppID()+"&secret="+Configure.getSecret()+"&js_code="+code+"&grant_type=authorization_code");
		//设置请求器的配置
		HttpClient httpClient = HttpClients.createDefault();
		HttpResponse res = httpClient.execute(httpGet);
		HttpEntity entity = res.getEntity();
		String result = EntityUtils.toString(entity, "UTF-8");
		if(StringUtils.isEmpty(result)){
			log.error("微信登陆未获取到openId！");
			return  ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		String token = createToken(request,result);
		if(StringUtils.isEmpty(token)){
			log.error("未获取到token");
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		String sessionId = request.getSession().getId();
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setSessionId(sessionId);
		headerInfo.setToken(token);
		return  ApiResponse.success(headerInfo);
	}

	/**
	 * 生成token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String createToken(HttpServletRequest request,String result){

		//生成token
		String token = UUID.randomUUID().toString().substring(0, 16);
		log.info("token = " + token);
		//解析session_key 和 openId
		JSONObject jsonObject = JSONObject.parseObject(result);
		if(jsonObject.isEmpty()){
			return null;
		}
		TokenBean tokenBean = new TokenBean();
		tokenBean.setOpenId(jsonObject.getString("openid"));
		tokenBean.setSession_key(jsonObject.getString("session_key"));
		tokenBean.setToken(token);
		tokenBean.setMerchantId(request.getParameter("merchantId"));
		tokenBean.setUserType(request.getParameter("userType"));
		log.info("生成tokenBean", tokenBean);
		TokenUtil.setHeader(tokenBean);
		//放入session中
		request.getSession().setAttribute(token, tokenBean);
		userService.addUserInfo(tokenBean);
		return token;
	}

	@PostMapping("/login")
	@ResponseBody
	public ApiResponse login(HttpServletRequest request, TUser userInfo) {
		String token = request.getHeader("token");
		TokenBean tokenBean = (TokenBean) request.getSession().getAttribute(token);
		if(userInfo == null){
			return ApiResponse.fail(ApiEnum.PARAM_NULL);
		}
		tokenBean.setNickName(userInfo.getNickName());
		tokenBean.setGender(userInfo.getGender());
		request.getSession().setAttribute(tokenBean.getToken(),tokenBean);
		tokenBean.setAvatarUrl(userInfo.getAvatarUrl());
		userService.updateUserInfo(tokenBean);
		return ApiResponse.success(token);
	}

}
