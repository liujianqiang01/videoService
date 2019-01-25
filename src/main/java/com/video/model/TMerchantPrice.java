package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TMerchantPrice implements Serializable {
    /**
     * 
     * 表字段 : t_merchant_price.id
     */
    private Integer id;

    /**
     * 商户ID
     * 表字段 : t_merchant_price.merchan_id
     */
    private String merchanId;

    /**
     * 月卡价格
     * 表字段 : t_merchant_price.month_card_price
     */
    private BigDecimal monthCardPrice;

    /**
     * 季卡价格
     * 表字段 : t_merchant_price.season_card_price
     */
    private BigDecimal seasonCardPrice;

    /**
     * 
     * 表字段 : t_merchant_price.year_card_price
     */
    private BigDecimal yearCardPrice;

    /**
     * 状态 0-申请，1-通过，2-失效
     * 表字段 : t_merchant_price.state
     */
    private Integer state;

    /**
     * 
     * 表字段 : t_merchant_price.create_time
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchanId() {
        return merchanId;
    }

    public void setMerchanId(String merchanId) {
        this.merchanId = merchanId == null ? null : merchanId.trim();
    }

    public BigDecimal getMonthCardPrice() {
        return monthCardPrice;
    }

    public void setMonthCardPrice(BigDecimal monthCardPrice) {
        this.monthCardPrice = monthCardPrice;
    }

    public BigDecimal getSeasonCardPrice() {
        return seasonCardPrice;
    }

    public void setSeasonCardPrice(BigDecimal seasonCardPrice) {
        this.seasonCardPrice = seasonCardPrice;
    }

    public BigDecimal getYearCardPrice() {
        return yearCardPrice;
    }

    public void setYearCardPrice(BigDecimal yearCardPrice) {
        this.yearCardPrice = yearCardPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", merchanId=").append(merchanId);
        sb.append(", monthCardPrice=").append(monthCardPrice);
        sb.append(", seasonCardPrice=").append(seasonCardPrice);
        sb.append(", yearCardPrice=").append(yearCardPrice);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}