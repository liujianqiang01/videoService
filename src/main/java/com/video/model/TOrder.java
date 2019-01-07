package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TOrder implements Serializable {
     //主键Id
    private Integer id;

     //订单编码
    private String orderCode;

     //订单状态 0-新建，1-待支付，2-支付成功
    private Integer orderState;

     //订单金额
    private BigDecimal orderPrice;

     //商户Id
    private String merchantId;

     //用户Id
    private String openId;

     //vip编码
    private String vipCode;

     //vip状态 0-失效，1-有效
    private Integer vipState;

     //vip开始时间
    private Date vipStartTime;

     //vip结束时间
    private Date vipEndTime;

     //三方订单
    private String thirdOederCode;
    //vip开始时间
    private String vipStartDate;

    //vip结束时间
    private String vipEndDate;

    //第三方预支付id
    private String prepayId;

    //卡名称
    private String vipName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode == null ? null : vipCode.trim();
    }

    public Integer getVipState() {
        return vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    public Date getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(Date vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public Date getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(Date vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public String getThirdOederCode() {
        return thirdOederCode;
    }

    public void setThirdOederCode(String thirdOederCode) {
        this.thirdOederCode = thirdOederCode == null ? null : thirdOederCode.trim();
    }

    public String getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(String vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(String vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", orderState=").append(orderState);
        sb.append(", orderPrice=").append(orderPrice);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", openId=").append(openId);
        sb.append(", vipCode=").append(vipCode);
        sb.append(", vipState=").append(vipState);
        sb.append(", vipStartTime=").append(vipStartTime);
        sb.append(", vipEndTime=").append(vipEndTime);
        sb.append(", thirdOederCode=").append(thirdOederCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}