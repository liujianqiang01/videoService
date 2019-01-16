package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TSettleAccount implements Serializable {
    /**
     * 主键
     * 表字段 : t_settle_account.id
     */
    private Integer id;

    /**
     * 汇款状态 0-未汇款，1-已汇款
     * 表字段 : t_settle_account.remittance_state
     */
    private Integer remittanceState;

    /**
     * 结算总金额
     * 表字段 : t_settle_account.settle_account_price
     */
    private BigDecimal settleAccountPrice;

    /**
     * 结算开始时间
     * 表字段 : t_settle_account.settle_account_start_time
     */
    private Date settleAccountStartTime;

    /**
     * 结算结束时间
     * 表字段 : t_settle_account.settle_account_end_time
     */
    private Date settleAccountEndTime;

    /**
     * 提成金额
     * 表字段 : t_settle_account.rate_price
     */
    private BigDecimal ratePrice;

    /**
     * 商户Id
     * 表字段 : t_settle_account.merchant_id
     */
    private String merchantId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemittanceState() {
        return remittanceState;
    }

    public void setRemittanceState(Integer remittanceState) {
        this.remittanceState = remittanceState;
    }

    public BigDecimal getSettleAccountPrice() {
        return settleAccountPrice;
    }

    public void setSettleAccountPrice(BigDecimal settleAccountPrice) {
        this.settleAccountPrice = settleAccountPrice;
    }

    public Date getSettleAccountStartTime() {
        return settleAccountStartTime;
    }

    public void setSettleAccountStartTime(Date settleAccountStartTime) {
        this.settleAccountStartTime = settleAccountStartTime;
    }

    public Date getSettleAccountEndTime() {
        return settleAccountEndTime;
    }

    public void setSettleAccountEndTime(Date settleAccountEndTime) {
        this.settleAccountEndTime = settleAccountEndTime;
    }

    public BigDecimal getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(BigDecimal ratePrice) {
        this.ratePrice = ratePrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", remittanceState=").append(remittanceState);
        sb.append(", settleAccountPrice=").append(settleAccountPrice);
        sb.append(", settleAccountStartTime=").append(settleAccountStartTime);
        sb.append(", settleAccountEndTime=").append(settleAccountEndTime);
        sb.append(", ratePrice=").append(ratePrice);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}