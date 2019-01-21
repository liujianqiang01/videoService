package com.video.model.ao;

import java.math.BigDecimal;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-20
 * @Description:
 */
public class WholesaleAo {
    private Integer vipType;
    private Integer number;
    private BigDecimal totalPrice;
    private BigDecimal price;
    private String vipName;

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    @Override
    public String toString() {
        return "WholesaleAo{" +
                "vipType=" + vipType +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                ", price=" + price +
                '}';
    }
}