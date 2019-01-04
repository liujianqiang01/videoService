package com.video.enumUtil;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-04
 * @Description:
 */
public enum EnumUtil {

    COMMION_USER_TYPE(0, "普通用户"),
    MERCHANT_USER_TYPE(1, "商户");
    int code;

    String message;
    EnumUtil(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}


