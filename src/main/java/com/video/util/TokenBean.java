package com.video.util;

/**
 * @Author: liujianqiang
 * @Date: 2018-12-31
 * @Description:
 */
public class TokenBean {

    // 登录凭证
    private String token;
    //会话密钥
    private String session_key;
    //用户唯一标识
    private String openId;
    //商户Id
    private String merchantId;
    //用户类型
    private String userType;
    //昵称
    private String nickName;
    // 性别 0：未知、1：男、2：女
    private Integer gender;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenId() {
        return openId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "token='" + token + '\'' +
                ", session_key='" + session_key + '\'' +
                ", openId='" + openId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", userType='" + userType + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                '}';
    }
}