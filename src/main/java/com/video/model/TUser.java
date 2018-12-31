package com.winterchen.model;

import java.io.Serializable;

public class TUser implements Serializable {
     //用户id
    private Integer userId;

     //用户名
    private String userName;

     //
    private String password;

     //电话
    private String phone;

     //用户微信唯一身份识别码
    private String openId;

     //微信昵称
    private String weixinName;

     //用户类型 0-普通用户，1-商户
    private Integer userType;

     //商户Id
    private String menchantId;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getWeixinName() {
        return weixinName;
    }

    public void setWeixinName(String weixinName) {
        this.weixinName = weixinName == null ? null : weixinName.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMenchantId() {
        return menchantId;
    }

    public void setMenchantId(String menchantId) {
        this.menchantId = menchantId == null ? null : menchantId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", phone=").append(phone);
        sb.append(", openId=").append(openId);
        sb.append(", weixinName=").append(weixinName);
        sb.append(", userType=").append(userType);
        sb.append(", menchantId=").append(menchantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}