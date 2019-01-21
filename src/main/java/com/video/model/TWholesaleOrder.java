package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TWholesaleOrder implements Serializable {
    /**
     * 主键
     * 表字段 : t_wholesale_order.id
     */
    private Integer id;

    /**
     * 采购订单编号
     * 表字段 : t_wholesale_order.order_code
     */
    private String orderCode;

    /**
     * 订单总金额
     * 表字段 : t_wholesale_order.order_price
     */
    private BigDecimal orderPrice;

    /**
     * 订单详情
     * 表字段 : t_wholesale_order.order_desc
     */
    private String orderDesc;

    /**
     * 创建时间
     * 表字段 : t_wholesale_order.create_time
     */
    private Date createTime;

    /**
     * 订单状态 1-待支付，2-已支付，3-支付成功，4-支付失败
     * 表字段 : t_wholesale_order.order_state
     */
    private Integer orderState;

    /**
     * 微信凭证
     * 表字段 : t_wholesale_order.open_id
     */
    private String openId;

    /**
     * 商户Id
     * 表字段 : t_wholesale_order.merchant_id
     */
    private String merchantId;

    /**
     * 单方订单
     * 表字段 : t_wholesale_order.third_order_code
     */
    private String thirdOrderCode;

    /**
     * 预支付订单
     * 表字段 : t_wholesale_order.prepay_id
     */
    private String prepayId;

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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getThirdOrderCode() {
        return thirdOrderCode;
    }

    public void setThirdOrderCode(String thirdOrderCode) {
        this.thirdOrderCode = thirdOrderCode == null ? null : thirdOrderCode.trim();
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId == null ? null : prepayId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", orderPrice=").append(orderPrice);
        sb.append(", orderDesc=").append(orderDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", orderState=").append(orderState);
        sb.append(", openId=").append(openId);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", thirdOrderCode=").append(thirdOrderCode);
        sb.append(", prepayId=").append(prepayId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}