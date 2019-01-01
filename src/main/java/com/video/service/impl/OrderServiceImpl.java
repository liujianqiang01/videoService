package com.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.dao.ITOrderMapper;
import com.video.dao.ITVipCodesMapper;
import com.video.dao.ITVipPriceMapper;
import com.video.model.Ao.OrderInfo;
import com.video.model.Ao.OrderReturnInfo;
import com.video.model.TOrder;
import com.video.model.TVipCodes;
import com.video.model.TVipPrice;
import com.video.service.OrderService;
import com.video.util.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    ITOrderMapper orderMapper;
    @Autowired
    ITVipPriceMapper vipPriceMapper;
    @Autowired
    ITVipCodesMapper vipCodesMapper;

    private static int count =1;

    @Override
    public ApiResponse subOrder(String spbill_create_ip, Integer vipType ) {
        JSONObject json = new JSONObject();
        try {
            TokenBean token = TokenUtil.getToken();
            if(token == null){
                log.info("---------下单 token : " + token);
                return ApiResponse.fail(ApiEnum.PARAM_NULL);
            }

            String openid = token.getOpenId();
            if(StringUtils.isEmpty(openid)){
                return ApiResponse.fail(ApiEnum.PARAM_NULL);
            }
            //获取vip价格
            TVipPrice vipPriceParam = new TVipPrice();
            vipPriceParam.setVipType(vipType);
            TVipPrice vipPrice = vipPriceMapper.selectByWhere(vipPriceParam);
            if(vipPrice == null){
                log.error("未能获取到对应的vip！");
                return ApiResponse.fail(ApiEnum.PARAM_NULL);
            }
            OrderInfo order = new OrderInfo();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setBody("影视会员VIP年卡"+ vipPrice.getVipType());
            order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(32));
            order.setTotal_fee(vipPrice.getVipPrice());
            order.setSpbill_create_ip(spbill_create_ip);
            order.setNotify_url("https://www.videoalliance.cn/PayResult");
            order.setTrade_type("JSAPI");
            order.setOpenid(openid);
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            addOrder(order,token, vipPrice);
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
            log.info("---------下单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            if(!"SUCCESS".equals(returnInfo.getReturn_code())){
                return ApiResponse.fail(ApiEnum.PARAM_ERROR);
            }
            json.put("prepayId", returnInfo.getPrepay_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-------", e);
        }
        return ApiResponse.success(json.toJSONString());
    }

    /**
     * 订单入库
     * @param orderInfo
     * @param token
     */
    private void addOrder(OrderInfo orderInfo, TokenBean token,TVipPrice vipPrice){
        TOrder order = new TOrder();
        BeanUtils.copyProperties(orderInfo,order);
        order.setMerchantId(token.getMerchantId());
        order.setOrderCode(orderInfo.getOut_trade_no());
        order.setOrderState(0);
        order.setOrderPrice(orderInfo.getTotal_fee());
        order.setOpenId(orderInfo.getOpenid());
        getVip(order,vipPrice);
        List<TOrder> orders = new ArrayList<>();
        orders.add(order);
        orderMapper.insertBatch(orders);
    }

    /**
     * 获取vip
     */
    private void getVip(TOrder order,TVipPrice vipPrice){
        TVipCodes tVipCodes = vipCodesMapper.selectOneByWhere(vipPrice.getVipType(),1,getCount());
        if(tVipCodes != null){
            order.setVipCode(tVipCodes.getVipCode());
            order.setVipState(1);
            order.setVipStartTime(new Date());
            long now = System.currentTimeMillis();
            long to = (86400*vipPrice.getIndate());
            order.setVipEndTime(new Date(now+to));
            tVipCodes.setVipState(0);
            vipCodesMapper.updateByPrimaryKeySelective(tVipCodes);
        }
    }

    /**
     * 简单的防止并发
     * @return
     */
    private synchronized int getCount(){
        count +=1;
        int next = count;
        if(count > 100){//大于100归0
            count = 0;
        }
        return next;
    }
}