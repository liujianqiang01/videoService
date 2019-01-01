package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TVipPrice implements Serializable {
     //
    private Integer id;

     //vip类型 0-免费试用，1-月卡，2-季卡，3-年卡
    private Integer vipType;

     //vip价格表
    private BigDecimal vipPrice;

     //有效期 /天
    private Integer indate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getIndate() {
        return indate;
    }

    public void setIndate(Integer indate) {
        this.indate = indate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vipType=").append(vipType);
        sb.append(", vipPrice=").append(vipPrice);
        sb.append(", indate=").append(indate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}