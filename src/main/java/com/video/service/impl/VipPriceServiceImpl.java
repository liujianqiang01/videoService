package com.video.service.impl;

import com.video.dao.ITMerchantPriceMapper;
import com.video.dao.ITVipPriceMapper;
import com.video.model.TMerchantPrice;
import com.video.model.TVipPrice;
import com.video.service.VipPriceService;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class VipPriceServiceImpl implements VipPriceService {
    @Autowired
    private ITVipPriceMapper vipPriceMapper;
    @Autowired
    private ITMerchantPriceMapper merchantPriceMapper;
    @Override
    public int insertBatch(List<TVipPrice> record) {
        return vipPriceMapper.insertBatch(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return vipPriceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TVipPrice selectByPrimaryKey(Integer id) {
        return vipPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TVipPrice record) {
        return vipPriceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TVipPrice> selectListByWhere(TVipPrice record) {
        TokenBean tokenBean = TokenUtil.getToken();
        TMerchantPrice priceParam = new TMerchantPrice();
        priceParam.setState(1);
        priceParam.setMerchanId(tokenBean.getMerchantId());

        TMerchantPrice merchantPrice = merchantPriceMapper.selectByClassElement(priceParam);
        List<TVipPrice> tVipPrices = vipPriceMapper.selectListByWhere(record);
        if(merchantPrice != null){
            for(TVipPrice t : tVipPrices){
                if(t.getVipType() == 1){
                    if(merchantPrice.getMonthCardPrice().compareTo(BigDecimal.ZERO) > 0 ){
                        t.setVipPrice(merchantPrice.getMonthCardPrice());
                    }
                }else  if(t.getVipType() == 2){
                    if(merchantPrice.getSeasonCardPrice().compareTo(BigDecimal.ZERO) >  0){
                        t.setVipPrice(merchantPrice.getSeasonCardPrice());
                    }
                }else  if(t.getVipType() == 3){
                    if(merchantPrice.getYearCardPrice().compareTo(BigDecimal.ZERO) > 0){
                        t.setVipPrice(merchantPrice.getYearCardPrice());
                    }
                }
            }
        }
        return tVipPrices;
    }

    @Override
    public TVipPrice selectByWhere(TVipPrice record) {
        return vipPriceMapper.selectByWhere(record);
    }
}