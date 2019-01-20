package com.video.model;

import java.io.Serializable;

public class TVipCodes implements Serializable {
    //
    private Integer id;

    //vip编码
    private String vipCode;

    //vip类型 0-免费试用，1-月卡，2-季卡，3-年卡
    private Integer vipType;

    //有效期 /天
    private Integer indate;

    //状态 0-失效，1-有效
    private Integer vipState;
    private String merchantId;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode == null ? null : vipCode.trim();
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getIndate() {
        return indate;
    }

    public void setIndate(Integer indate) {
        this.indate = indate;
    }

    public Integer getVipState() {
        return vipState;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vipCode=").append(vipCode);
        sb.append(", vipType=").append(vipType);
        sb.append(", indate=").append(indate);
        sb.append(", vipState=").append(vipState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}