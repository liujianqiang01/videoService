package com.video.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.video.dao.ITVipCodesMapper;
import com.video.dao.ITWholesalePriceMapper;
import com.video.model.TVipCodes;
import com.video.model.TWholesaleOrder;
import com.video.model.TWholesalePrice;
import com.video.model.ao.WholesaleAo;
import com.video.service.WholesalePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(WholesalePriceServiceImpl.class);

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
            codes.setVipState(1);
            String vipName = "年卡";
            if(vipType == 1){
                vipName = "月卡";
            }else if (vipType == 2){
                vipName = "季卡";
            }
            int stock = vipCodesMapper.countByWhere(codes);
            if(number+100 < stock) {
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

    /**
     * 分发
     */
    public void handOut(TWholesaleOrder order){
        JSONArray array= JSONArray.parseArray(order.getOrderDesc());
        for(int i = 0; i<array.size(); i++){
            Integer vipType = array.getJSONObject(i).getInteger("vipType");
            Integer number = array.getJSONObject(i).getInteger("number");
            //检查后台激活码是否足够
            TVipCodes codes = new TVipCodes();
            codes.setVipType(vipType);
            codes.setMerchantId("Admin");
            codes.setVipState(1);
            int stock = vipCodesMapper.countByWhere(codes);
            if(number+100 < stock) {
                //每一百次一提交
                int a = number;
                int b = 100;
                int size = (a/b)+1;
                PageInfo<TVipCodes> orderPageInfo ;
                for(int j = 0; j < size; j++) {
                    List<Integer> ids = new ArrayList<>();
                    try {
                        if (a > b) {
                            orderPageInfo = PageHelper.startPage(0, b).setOrderBy("id desc").doSelectPageInfo(() -> vipCodesMapper.selectListByWhere(codes));
                        } else {
                            orderPageInfo = PageHelper.startPage(0, a).setOrderBy("id desc").doSelectPageInfo(() -> vipCodesMapper.selectListByWhere(codes));
                        }
                        //组装id
                        for (TVipCodes v : orderPageInfo.getList()) {
                            ids.add(v.getId());
                        }
                        vipCodesMapper.updateMerchantByPrimaryKey(order.getMerchantId(), ids);
                    }catch (Exception e){
                        log.error("采购单次失败 merchant = "+order.getMerchantId() + "idSize = "+ids.size(),e);
                    }
                }
            }
        }
    }
}