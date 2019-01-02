package com.video.util;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public enum ApiEnum {

    SYSTEM_ERROR(-1, "系统异常"),
    PARAM_NULL(-2, "参数为空"),
    RETURN_ERROR(-3, "返回异常"),
   PARAM_ERROR(-4, "参数异常");
    int errCode;

    String errMsg;
    ApiEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }}
