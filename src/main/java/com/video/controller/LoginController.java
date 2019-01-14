package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.video.common.Configure;
import com.video.model.Ao.HeaderInfo;
import com.video.model.TUser;
import com.video.service.UserService;
import com.video.enumUtil.ApiEnum;
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
import java.util.List;
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
		log.info("登陆参数：code="+code);
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
		log.error("微信获取openId= "+result);
		if(StringUtils.isEmpty(result)){
			log.error("微信登陆未获取到openId！");
			return  ApiResponse.fail(ApiEnum.OPENID_NULL);
		}
		TokenBean token = createToken(request,result);
		if(StringUtils.isEmpty(token.getToken())){
			log.error("未获取到token");
			return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
		}
		String sessionId = request.getSession().getId();
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setSessionId(sessionId);
		headerInfo.setToken(token.getToken());
		headerInfo.setMerchantId(token.getMerchantId());
		headerInfo.setUserType(token.getUserType());
		return  ApiResponse.success(headerInfo);
	}

	/**
	 * 生成token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TokenBean createToken(HttpServletRequest request,String result){

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
		String param = request.getParameter("param");
		log.info("登陆参数：param ="+param );
		if(StringUtils.isEmpty(param)){
			return null;
		}
		String[] params = param.split(",");
		if(params.length <=1){
			return null;
		}
		String merchantId = params[0];
		String userTypeStr = params[1];
		log.info("登陆参数：merchantId ="+merchantId +"，userType="+userTypeStr);
		Integer userType ;
		if(StringUtils.isEmpty(userTypeStr)){
			userType = null;
		}else {
			userType = Integer.valueOf(userTypeStr);
		}
		//用户第一次绑定商户，那么永久属于第一次绑定的商户
		List<TUser> tuser = userService.getTuser(tokenBean.getOpenId());
		if(tuser != null && tuser.size()>0){
			if(tuser.size() == 1) {
				merchantId = tuser.get(0).getMenchantId();
				userType = tuser.get(0).getUserType();
			}
			//如果有多个那么默认返回商户
			for(TUser user : tuser){
				if(user.getUserType() == 1){
					merchantId = user.getMenchantId();
					userType = user.getUserType();
				}
			}
		}
		//如果未查到用户绑定信息，那么就用平台的信息
		if(StringUtils.isEmpty(merchantId) || userType == null){
			merchantId = "Admin";
			userType = 0;
		}
		tokenBean.setMerchantId(merchantId);
		tokenBean.setUserType(userType);
		log.info("生成tokenBean", tokenBean);
		TokenUtil.setHeader(tokenBean);
		//放入session中
		request.getSession().setAttribute(token, tokenBean);
		userService.addUserInfo(tokenBean);
		return tokenBean;
	}

	@PostMapping("/login")
	@ResponseBody
	public ApiResponse login(HttpServletRequest request, TUser userInfo) {
		String token = request.getHeader("token");
		TokenBean tokenBean = (TokenBean) request.getSession().getAttribute(token);
		if(userInfo == null){
			return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
		}
		tokenBean.setNickName(userInfo.getNickName());
		tokenBean.setGender(userInfo.getGender());
		request.getSession().setAttribute(tokenBean.getToken(),tokenBean);
		tokenBean.setAvatarUrl(userInfo.getAvatarUrl());
		userService.updateUserInfo(tokenBean);
		return ApiResponse.success(token);
	}

}
