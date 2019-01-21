package com.video.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TWholesalePrice implements Serializable {
    /**
     * 主键
     * 表字段 : t_wholesale_price.id
     */
    private Integer id;

    /**
     * vip类型 1-月卡，2-季卡，3-年卡
     * 表字段 : t_wholesale_price.vip_type
     */
    private Integer vipType;

    /**
     * 采购价格
     * 表字段 : t_wholesale_price.vip_price
     */
    private BigDecimal vipPrice;

    /**
     * 采购数量区间开始
     * 表字段 : t_wholesale_price.vip_count_start
     */
    private Integer vipCountStart;

    /**
     * 采购数量区间结束
     * 表字段 : t_wholesale_price.vip_count_end
     */
    private Integer vipCountEnd;

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

    public Integer getVipCountStart() {
        return vipCountStart;
    }

    public void setVipCountStart(Integer vipCountStart) {
        this.vipCountStart = vipCountStart;
    }

    public Integer getVipCountEnd() {
        return vipCountEnd;
    }

    public void setVipCountEnd(Integer vipCountEnd) {
        this.vipCountEnd = vipCountEnd;
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
        sb.append(", vipCountStart=").append(vipCountStart);
        sb.append(", vipCountEnd=").append(vipCountEnd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}