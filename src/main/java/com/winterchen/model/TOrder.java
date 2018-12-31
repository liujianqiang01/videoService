package com.winterchen.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TOrder implements Serializable {
     //主键Id
    private Integer id;

     //订单编码
    private Integer orderCode;

     //订单状态 0-新建，1-待支付，2-支付成功
    private Integer orderState;

     //订单金额
    private BigDecimal orderPrice;

     //促销id
    private Integer activityId;

     //用户Id
    private Integer userId;

     //vip编码
    private String vipCode;

     //vip状态 0-失效，1-有效
    private Integer vipState;

     //vip开始时间
    private Date vipStartTime;

     //vip结束时间
    private Date vipEndTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        sb.append(", activityId=").append(activityId);
        sb.append(", userId=").append(userId);
        sb.append(", vipCode=").append(vipCode);
        sb.append(", vipState=").append(vipState);
        sb.append(", vipStartTime=").append(vipStartTime);
        sb.append(", vipEndTime=").append(vipEndTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}