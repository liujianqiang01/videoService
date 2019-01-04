package com.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.dao.ITOrderMapper;
import com.video.dao.ITUserMapper;
import com.video.dao.ITVipCodesMapper;
import com.video.dao.ITVipPriceMapper;
import com.video.enumUtil.EnumUtil;
import com.video.enumUtil.TradeState;
import com.video.model.Ao.OrderInfo;
import com.video.model.Ao.OrderReturnInfo;
import com.video.model.Ao.PayResultInfo;
import com.video.model.TOrder;
import com.video.model.TUser;
import com.video.model.TVipCodes;
import com.video.model.TVipPrice;
import com.video.service.OrderService;
import com.video.enumUtil.ApiEnum;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    ITUserMapper userMapper;
    private static int count =0;

    @Override
    public ApiResponse subOrder( Integer vipType ) {
        JSONObject json = new JSONObject();
        try {
            TokenBean token = TokenUtil.getToken();
            if(token == null){
                log.info("---------下单 token : " + token);
                return ApiResponse.fail(ApiEnum.PARAM_NULL);
            }

            String openid = token.getOpenId();
            if(StringUtils.isEmpty(openid)){
                log.info("---------下单 openid : " + openid);
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
            order.setTotal_fee(vipPrice.getVipPrice().multiply(BigDecimal.valueOf(100)).setScale(0));
            order.setSpbill_create_ip(Configure.getSpbill_create_ip());
            order.setNotify_url("https://www.filmunion.com.cn/payResult");
            order.setTrade_type("JSAPI");
            order.setOpenid(openid);
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            int suss = addOrder(order,token, vipPrice);
            if(suss == 0){
                return ApiResponse.fail(ApiEnum.RETURN_ERROR);
            }
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
            log.info("---------下单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);

            if("SUCCESS".equals(returnInfo.getReturn_code()) && "SUCCESS".equals(returnInfo.getResult_code())){
                json.put("prepayId", returnInfo.getPrepay_id());
            }else {
                log.error(returnInfo.getReturn_msg());
                return ApiResponse.fail(ApiEnum.PARAM_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-------", e);
        }
        return ApiResponse.success(json.toJSONString());
    }

    @Override
    public List<TOrder> getOrder(String openId, Integer userType) {
        TOrder param = new TOrder();
        if(userType.equals(EnumUtil.MERCHANT_USER_TYPE.getCode())){
            TUser userParam = new TUser();
            userParam.setOpenId(openId);
            TUser userR = userMapper.selectByWhere(userParam);
            if(userR != null) {
                param.setMerchantId(userR.getMenchantId());
               return orderMapper.selectListByWhere(param);
            }
            return null;
        }else {
            param.setOpenId(openId);
            return orderMapper.selectListByWhere(param);
        }
    }

    @Override
    public void successOrder(PayResultInfo param) {
        TOrder order = new TOrder();
        order.setOrderCode(param.getOut_trade_no());
        TOrder orderResult = orderMapper.selectByWhere(order);

        TOrder update = new TOrder();
        update.setId(orderResult.getId());
        update.setOrderState(2);
        update.setVipState(1);
        update.setThirdOederCode(param.getTransaction_id());
        orderMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public void unSuccessOrder(PayResultInfo order) {
        TOrder param = new TOrder();
        param.setOrderCode(order.getOut_trade_no());
        TOrder orderResult = orderMapper.selectByWhere(param);
        if(orderResult != null) {
            TOrder update = new TOrder();
            update.setId(orderResult.getId());
            update.setThirdOederCode(order.getOut_trade_no());
            if (order.getTrade_state().equals(TradeState.CLOSED.getCode()) || order.getTrade_state().equals(TradeState.PAYERROR.getCode())
                    || order.getTrade_state().equals(TradeState.REVOKED.getCode())) {
                update.setOrderState(3);
            }else if(order.getTrade_state().equals(TradeState.NOTPAY.getCode())||order.getTrade_state().equals(TradeState.USERPAYING.getCode())){
                update.setOrderState(1);
            }
            orderMapper.updateByPrimaryKeySelective(update);
        }
    }

    /**
     * 订单入库
     * @param orderInfo
     * @param token
     */
    private int addOrder(OrderInfo orderInfo, TokenBean token,TVipPrice vipPrice){
        TOrder order = new TOrder();
        BeanUtils.copyProperties(orderInfo,order);
        order.setMerchantId(token.getMerchantId());
        order.setOrderCode(orderInfo.getOut_trade_no());
        order.setOrderState(0);
        order.setOrderPrice(orderInfo.getTotal_fee());
        order.setOpenId(orderInfo.getOpenid());
        getVip(order,vipPrice);
        if(!StringUtils.isEmpty(order.getVipCode())) {
            List<TOrder> orders = new ArrayList<>();
            orders.add(order);
            orderMapper.insertBatch(orders);
            return 1;
        }
        return 0;
    }

    /**
     * 获取vip
     */
    private void getVip(TOrder order,TVipPrice vipPrice){
        TVipCodes tVipCodes = vipCodesMapper.selectOneByWhere(vipPrice.getVipType(),1,getCount());
        if(tVipCodes != null){
            order.setVipCode(tVipCodes.getVipCode());
            order.setVipState(0);
            order.setVipStartTime(new Date());
            long now = System.currentTimeMillis();
            long to = (86400000*vipPrice.getIndate());
            order.setVipEndTime(new Date(now+to));
            tVipCodes.setVipState(0);
            vipCodesMapper.updateByPrimaryKeySelective(tVipCodes);
        }else{
            log.error("未获取到会员卡信息！");
        }
    }

    /**
     * 简单的防止并发
     * @return
     */
    private synchronized int getCount(){
        count +=1;
        int next = count;
        if(count > 2){//大于100归0
            count = 0;
        }
        return next;
    }
}