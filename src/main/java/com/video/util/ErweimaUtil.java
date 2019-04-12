package com.video.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.video.common.Configure;

import com.video.common.HttpRequest;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-02-26
 * @Description:
 */
public class ErweimaUtil {
    private static Logger log = LoggerFactory.getLogger(ErweimaUtil.class);

    /*
     * 获取二维码
     * 这里的 post 方法 为 json post【重点】
     */
    public static byte[] getCodeM(String merchantId) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String para = "?appid=" + Configure.getAppID() + "&secret=" + Configure.getSecret() + "&grant_type=client_credential";
        String result = HttpRequest.sendGet(url, para);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.isEmpty()) {
            return null;
        }
        String access_token = jsonObject.getString("access_token");
        log.info("==========>access_token="+access_token);
        if(!StringUtils.isEmpty(access_token)){
            RestTemplate rest = new RestTemplate();
            try {
                String url1 = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
                Map<String, Object> param = new HashMap<>();
                param.put("scene", merchantId);
                param.put("page", "pages/index/index");
                param.put("width", 430);
                param.put("auto_color", false);
                Map<String, Object> line_color = new HashMap<>();
                line_color.put("r", 0);
                line_color.put("g", 0);
                line_color.put("b", 0);
                param.put("line_color", line_color);
                log.info("调用生成微信URL接口传参:" + param);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                HttpEntity requestEntity = new HttpEntity(param, headers);
                ResponseEntity<byte[]> entity = rest.exchange(url1, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
                log.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
                byte[] result1 = entity.getBody();
               return result1;
            }catch (Exception e){
                log.info("小程序生成二维码失败",e);
            }
        }
        return null;
    }
}