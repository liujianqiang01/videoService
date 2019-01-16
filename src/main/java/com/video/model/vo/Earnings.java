package com.video.model.vo;

import java.math.BigDecimal;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-16
 * @Description:
 */
public class Earnings {
    //历史收益
    private BigDecimal earning;
    //未收益
    private BigDecimal unEarning;

    public BigDecimal getEarning() {
        return earning;
    }

    public void setEarning(BigDecimal earning) {
        this.earning = earning;
    }

    public BigDecimal getUnEarning() {
        return unEarning;
    }

    public void setUnEarning(BigDecimal unEarning) {
        this.unEarning = unEarning;
    }
}