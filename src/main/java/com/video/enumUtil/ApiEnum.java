package com.video.enumUtil;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public enum ApiEnum {

    SYSTEM_ERROR(-1, "系统异常"),
    PARAM_NULL(-2, "参数为空"),
    RETURN_ERROR(-3, "返回异常"),
    PARAM_ERROR(-4, "参数异常"),
    OPENID_NULL(-5,"openId为空"),
    TOKEN_ERROR(-6, "登陆异常"),
    VIP_NULL(-7,"激活码已售空"),
    SUB_ORDER_ERROR(-8,"提交订单失败"),
    NOT_MERCHANT(-9,"非商户类型");
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
