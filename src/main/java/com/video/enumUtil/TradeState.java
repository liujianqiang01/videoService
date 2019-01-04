package com.video.enumUtil;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-04
 * @Description:
 */
public enum  TradeState {
    SUCCESS("SUCCESS", "支付成功"),
    REFUND("REFUND", "转入退款"),
    NOTPAY("NOTPAY", "未支付"),
    CLOSED("CLOSED", "关闭"),
    REVOKED("REVOKED", "已撤销"),
    USERPAYING("USERPAYING", "用户支付中"),
    PAYERROR("PAYERROR", "支付失败(");

    String code;
    String message;
    TradeState(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}