package com.video.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.video.common.Configure;
import com.video.model.ao.HeaderInfo;
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
    public ApiResponse getToken(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        log.info("登陆参数：code=" + code);
        if (StringUtils.isEmpty(code)) {
            log.error("微信登陆code为空！");
            return ApiResponse.fail(ApiEnum.PARAM_NULL);
        }
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + Configure.getAppID() + "&secret=" + Configure.getSecret() + "&js_code=" + code + "&grant_type=authorization_code");
        //设置请求器的配置
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        log.error("微信获取openId= " + result);
        if (StringUtils.isEmpty(result)) {
            log.error("微信登陆未获取到openId！");
            return ApiResponse.fail(ApiEnum.OPENID_NULL);
        }
        TokenBean token = createToken(request, result);
        if (StringUtils.isEmpty(token.getToken())) {
            log.error("未获取到token");
            return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
        }
        String sessionId = request.getSession().getId();
        HeaderInfo headerInfo = new HeaderInfo();
        headerInfo.setSessionId(sessionId);
        headerInfo.setToken(token.getToken());
        headerInfo.setMerchantId(token.getMerchantId());
        headerInfo.setUserType(token.getUserType());
        return ApiResponse.success(headerInfo);
    }

    /**
     * 生成token
     *
     * @param request
     * @return
     * @throws Exception
     */
    public TokenBean createToken(HttpServletRequest request, String result) {

        //生成token
        String token = UUID.randomUUID().toString().substring(0, 16);
        log.info("token = " + token);
        //解析session_key 和 openId
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.isEmpty()) {
            return null;
        }
        TokenBean tokenBean = new TokenBean();
        tokenBean.setOpenId(jsonObject.getString("openid"));
        tokenBean.setSession_key(jsonObject.getString("session_key"));
        tokenBean.setToken(token);
        String merchantId = request.getParameter("merchantId");
        log.info("登陆参数：merchantId =" + merchantId);
        tokenBean.setMerchantId(merchantId);
        //查询用户是否已经存在绑定
        List<TUser> tuser = userService.getTuser(tokenBean.getOpenId());
        TUser user = null;
        //获取用户默认类型
        if(tuser != null && tuser.size() > 0){
            user = tuser.get(0);
            //默认返回商户
            for (TUser u : tuser) {
                if (u.getUserType() == 1) {
                    user = u;
                }
            }
        }
        // 判断用户是否已经绑定过商户和普通用户,如果都绑定，直接返回
        if (tuser != null && tuser.size() > 1) {
            tokenBean.setUserType(user.getUserType());
            tokenBean.setMerchantId(user.getMenchantId());
        } else {
            //搜索登陆,并且没有绑定任何商户
            if(StringUtils.isEmpty(merchantId) && user == null){
                tokenBean.setMerchantId("Admin");
            }else if(StringUtils.isEmpty(merchantId)){
                tokenBean.setMerchantId(user.getMenchantId());
            }
            //如果已经绑定，添加绑定的类型，此处只有绑定一种关系
            if(user != null){
                tokenBean.setUserType(user.getUserType());
            }
            log.info("用户类型：userType =" + tokenBean.getUserType());
            userService.addUserInfo(tokenBean);
        }
        //放入session中
        request.getSession().setAttribute(token, tokenBean);
        log.info("生成tokenBean", tokenBean);
        TokenUtil.setHeader(tokenBean);
        return tokenBean;
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse login(HttpServletRequest request, TUser userInfo) {
        String token = request.getHeader("token");
        TokenBean tokenBean = (TokenBean) request.getSession().getAttribute(token);
        if (userInfo == null) {
            return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
        }
        tokenBean.setNickName(userInfo.getNickName());
        tokenBean.setGender(userInfo.getGender());
        request.getSession().setAttribute(tokenBean.getToken(), tokenBean);
        tokenBean.setAvatarUrl(userInfo.getAvatarUrl());
        userService.updateUserInfo(tokenBean);
        return ApiResponse.success(token);
    }

}
