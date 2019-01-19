package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TMerchant implements Serializable {
     //主键
    private Integer id;

     //商户id
    private String menchantId;

     //商户名称
    private String menchantName;

    private String menchantAddr;

    private String mobile;

    private BigDecimal rate;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenchantId() {
        return menchantId;
    }

    public void setMenchantId(String menchantId) {
        this.menchantId = menchantId == null ? null : menchantId.trim();
    }

    public String getMenchantName() {
        return menchantName;
    }

    public void setMenchantName(String menchantName) {
        this.menchantName = menchantName == null ? null : menchantName.trim();
    }

    public String getMenchantAddr() {
        return menchantAddr;
    }

    public void setMenchantAddr(String menchantAddr) {
        this.menchantAddr = menchantAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menchantId=").append(menchantId);
        sb.append(", menchantName=").append(menchantName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}