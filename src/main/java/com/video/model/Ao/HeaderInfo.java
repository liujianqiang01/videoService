package com.video.model.Ao;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public class HeaderInfo {
    private String sessionId;
    private String token;
    private Integer userType;
    private String merchantId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}