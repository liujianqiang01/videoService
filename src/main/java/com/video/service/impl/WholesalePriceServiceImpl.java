package com.video.service.impl;

import com.video.dao.ITVipCodesMapper;
import com.video.dao.ITWholesalePriceMapper;
import com.video.model.TVipCodes;
import com.video.model.TWholesalePrice;
import com.video.model.ao.WholesaleAo;
import com.video.service.WholesalePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-20
 * @Description:
 */
@Service
public class WholesalePriceServiceImpl implements WholesalePriceService {

    @Autowired
    private ITWholesalePriceMapper wholesalePriceMapper;
    @Autowired
    private ITVipCodesMapper vipCodesMapper;

    @Override
    public Map<String,Object> getCountPrice(String[] numbers) {
        Map<String,Object> map = new HashMap<>();
        List<WholesaleAo> list = new ArrayList<>();
        //统计月卡
        Integer number1 = Integer.valueOf(numbers[0]);
        checkVipCodes(1,number1,list);
        //统计季卡
        Integer number2 = Integer.valueOf(numbers[1]);
        checkVipCodes(2,number2,list);
        //统计年卡
        Integer number3 = Integer.valueOf(numbers[2]);
        checkVipCodes(3,number3,list);
        map.put("list",list);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(WholesaleAo ao : list){
            totalPrice = totalPrice.add(ao.getTotalPrice());
        }
        map.put("totalPrice",totalPrice);
        return map;
    }

    private void checkVipCodes(Integer vipType, Integer number, List<WholesaleAo> list ){
        if(number > 0){
            //检查后台激活码是否足够
            TVipCodes codes = new TVipCodes();
            codes.setVipType(vipType);
            codes.setMerchantId("Admin");
            String vipName = "年卡";
            if(vipType == 1){
                vipName = "月卡";
            }else if (vipType == 2){
                vipName = "季卡";
            }
            int stock = vipCodesMapper.countByWhere(codes);
            if(1 < stock) {
                List<TWholesalePrice> tWholesalePrices = wholesalePriceMapper.selectBetween(vipType, number);
                if(tWholesalePrices != null && tWholesalePrices.size()>0){
                    BigDecimal vipPrice = tWholesalePrices.get(0).getVipPrice();
                    if(vipPrice.compareTo(BigDecimal.ZERO) > 0){
                        WholesaleAo ao = new WholesaleAo();
                        ao.setNumber(number);
                        ao.setVipType(vipType);
                        ao.setPrice(vipPrice);
                        ao.setVipName(vipName);
                        ao.setTotalPrice(vipPrice.multiply(BigDecimal.valueOf(number)));
                        list.add(ao);
                    }
                }
            }
        }
    }
}