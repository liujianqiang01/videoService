package com.video.model.ao;

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
    private boolean showPay = false;

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

    public boolean isShowPay() {
        return showPay;
    }

    public void setShowPay(boolean showPay) {
        this.showPay = showPay;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}

