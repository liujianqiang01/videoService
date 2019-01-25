package com.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.dao.*;
import com.video.enumUtil.EnumUtil;
import com.video.enumUtil.TradeState;
import com.video.model.*;
import com.video.model.ao.*;
import com.video.model.vo.Earnings;
import com.video.service.OrderService;
import com.video.enumUtil.ApiEnum;
import com.video.service.WholesaleOrderService;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ITOrderMapper orderMapper;
    @Autowired
    private ITVipPriceMapper vipPriceMapper;
    @Autowired
    private ITVipCodesMapper vipCodesMapper;
    @Autowired
    private ITUserMapper userMapper;
    @Autowired
    private ITMerchantMapper merchantMapper;
    @Autowired
    private ITSettleAccountMapper settleAccountMapper;
    @Autowired
    private WholesaleOrderService wholesaleOrderService;
    @Autowired
    private ITMerchantPriceMapper merchantPriceMapper;
    private static int count = -1;

    @Override
    public ApiResponse subOrder(Integer vipType) {
        JSONObject json = new JSONObject();
        try {
            TokenBean token = TokenUtil.getToken();
            log.info("---------下单 token : " + token);
            if (token == null) {
                return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
            }

            String openid = token.getOpenId();
            log.info("---------下单 openid : " + openid);
            if (StringUtils.isEmpty(openid)) {
                return ApiResponse.fail(ApiEnum.OPENID_NULL);
            }
            log.info("---------下单 merchantId : " + token.getMerchantId());
            if(StringUtils.isEmpty(token.getMerchantId())){
                return ApiResponse.fail(ApiEnum.NOT_MERCHANT);
            }
            //检查用户是否为系统用户
            TUser userParam = new TUser();
            userParam.setOpenId(openid);
            userParam.setMenchantId(token.getMerchantId());
            List<TUser> user = userMapper.selectListByWhere(userParam);
            if(user == null){
                return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
            }
            //获取vip价格
            TVipPrice vipPriceParam = new TVipPrice();
            vipPriceParam.setVipType(vipType);
            TVipPrice vipPrice = vipPriceMapper.selectByWhere(vipPriceParam);
            //校验商户是否有自己的价格
            TMerchantPrice priceParam = new TMerchantPrice();
            priceParam.setState(1);
            priceParam.setMerchanId(token.getMerchantId());
            TMerchantPrice merchantPrice = merchantPriceMapper.selectByClassElement(priceParam);
            if(vipType == 1){
                if(merchantPrice.getMonthCardPrice().compareTo(BigDecimal.ZERO) > 0 ){
                    vipPrice.setVipPrice(merchantPrice.getMonthCardPrice());
                }
            }else  if(vipType == 2){
                if(merchantPrice.getSeasonCardPrice().compareTo(BigDecimal.ZERO) >  0){
                    vipPrice.setVipPrice(merchantPrice.getSeasonCardPrice());
                }
            }else  if(vipType == 3){
                if(merchantPrice.getYearCardPrice().compareTo(BigDecimal.ZERO) > 0){
                    vipPrice.setVipPrice(merchantPrice.getYearCardPrice());
                }
            }
            if (vipPrice == null) {
                log.error("未能获取到对应的vip！");
                return ApiResponse.fail(ApiEnum.VIP_NULL);
            }
            OrderInfo order = new OrderInfo();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setBody("影视会员VIP" + vipPrice.getVipType());
            order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(20));
            order.setTotal_fee(vipPrice.getVipPrice().multiply(BigDecimal.valueOf(100)).setScale(0));
            order.setSpbill_create_ip(Configure.getSpbill_create_ip());
            order.setNotify_url(Configure.getPayResult());
            order.setTrade_type("JSAPI");
            order.setOpenid(openid);
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            int suss = addOrder(order, token, vipPrice);
            if (suss == 0) {
                return ApiResponse.fail(ApiEnum.VIP_NULL);
            }
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
            log.info("---------下单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);

            if ("SUCCESS".equals(returnInfo.getReturn_code()) && "SUCCESS".equals(returnInfo.getResult_code())) {
                json.put("prepayId", returnInfo.getPrepay_id());
                updateOrder(order.getOut_trade_no(), returnInfo.getPrepay_id());
            } else {
                log.error(returnInfo.getReturn_msg());
                return ApiResponse.fail(ApiEnum.SUB_ORDER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("-------", e);
        }
        return ApiResponse.success(json.toJSONString());
    }


    @Override
    public  PageInfo<TOrder>  getOrder(String openId, Integer userType,int pageNum,int pageSize) {
        TOrder param = new TOrder();
        if (userType.equals(EnumUtil.MERCHANT_USER_TYPE.getCode())) {
            TUser userParam = new TUser();
            userParam.setOpenId(openId);
            List<TUser> userR = userMapper.selectListByWhere(userParam);
            if (userR != null) {
                for(TUser u : userR){
                    if(u.getUserType() == 1) {
                        param.setMerchantId(u.getMenchantId());
                    }
                }
                PageInfo<TOrder> orderPageInfo = PageHelper.startPage(pageNum, pageSize).setOrderBy("id desc").doSelectPageInfo(() -> orderMapper.selectJojnListByWhere(param));
                return orderPageInfo;
            }
            return null;
        } else {
            param.setOpenId(openId);
            PageInfo<TOrder> orderPageInfo = PageHelper.startPage(pageNum, pageSize).setOrderBy("id desc").doSelectPageInfo(() -> orderMapper.selectJojnListByWhere(param));
            return orderPageInfo;
        }
    }

    @Override
    public boolean successOrder(PayResultInfo param) {
        TOrder order = new TOrder();
        order.setOrderCode(param.getOut_trade_no());
        TOrder orderResult = orderMapper.selectByWhere(order);
        if (orderResult != null && orderResult.getOrderState() <= 2) {
            TOrder update = new TOrder();
            update.setId(orderResult.getId());
            update.setOrderState(3);
            update.setVipState(1);
            update.setThirdOederCode(param.getTransaction_id());
            orderMapper.updateByPrimaryKeySelective(update);
            return true;
        }
        if(wholesaleOrderService.successOrder(param)){
            return true;
        }
        return false;
    }

    @Override
    public void unSuccessOrder(PayResultInfo order) {
        TOrder param = new TOrder();
        param.setOrderCode(order.getOut_trade_no());
        param.setOrderState(1);
        TOrder orderResult = orderMapper.selectByWhere(param);
        if (orderResult != null) {
            TOrder update = new TOrder();
            update.setId(orderResult.getId());
            update.setOrderState(2);
            orderMapper.updateByPrimaryKeySelective(update);
            return;
        }
        wholesaleOrderService.unSuccessOrder(order);
    }

    public void updateOrder(String orderCode, String prepayId) {
        TOrder param = new TOrder();
        param.setOrderCode(orderCode);
        TOrder orderResult = orderMapper.selectByWhere(param);
        if (orderResult != null) {
            TOrder update = new TOrder();
            update.setId(orderResult.getId());
            update.setPrepayId(prepayId);
            orderMapper.updateByPrimaryKeySelective(update);
        }
    }

    /**
     * 订单入库
     *
     * @param orderInfo
     * @param token
     */
    private int addOrder(OrderInfo orderInfo, TokenBean token, TVipPrice vipPrice) {
        TOrder order = new TOrder();
        BeanUtils.copyProperties(orderInfo, order);
        order.setMerchantId(token.getMerchantId());
        order.setOrderCode(orderInfo.getOut_trade_no());
        order.setOrderState(1);
        order.setOrderPrice(orderInfo.getTotal_fee().divide(BigDecimal.valueOf(100)).setScale(2));
        order.setOpenId(orderInfo.getOpenid());
        getVip(order, vipPrice);
        if (!StringUtils.isEmpty(order.getVipCode())) {
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
    private void getVip(TOrder order, TVipPrice vipPrice) {
        TokenBean tokenBean = TokenUtil.getToken();
        if(tokenBean == null || StringUtils.isEmpty(tokenBean.getMerchantId())){
            log.error("tokenBean 为空！");
            return;
        }
        //如果是代理商那么先查看一下代理商是否批发

        TVipCodes tVipCodes = null ;
        if(!"Admin".equals(tokenBean.getMerchantId())){
            tVipCodes = vipCodesMapper.selectOneByWhere(vipPrice.getVipType(), 1, getCount(),tokenBean.getMerchantId());
        }
        if(tVipCodes == null){
            tVipCodes = vipCodesMapper.selectOneByWhere(vipPrice.getVipType(), 1, getCount(), "Admin");
        }
        if (tVipCodes != null) {
            order.setVipCode(tVipCodes.getVipCode());
            order.setVipState(0);
            order.setVipStartTime(new Date());
            long now = System.currentTimeMillis();
            long to = (86400000L * vipPrice.getIndate());
            order.setVipEndTime(new Date(now + to));
            tVipCodes.setVipState(0);
            vipCodesMapper.updateByPrimaryKeySelective(tVipCodes);
        } else {
            log.error("未获取到会员卡信息！");
        }
    }

    /**
     * 简单的防止并发
     *
     * @return
     */
    private synchronized int getCount() {
        count += 1;
        int next = count;
        if (count > 5) {//大于100归0
            count = 0;
        }
        return next;
    }

    @Override
    @Transactional
    public void unPayTask() {
        long time = System.currentTimeMillis();
        Date date = new Date(time - 3600000);
        List<TOrder> timeOutOrder = orderMapper.getTimeOutOrder(date);
        TOrder param;
        TVipCodes update;
        if (timeOutOrder != null) {
            for (TOrder o : timeOutOrder) {
                log.info("订单超时取消 orderCode = " + o.getOrderCode());
                param = new TOrder();
                param.setId(o.getId());
                param.setOrderState(4);
                param.setVipState(0);
                //订单设置成支付失败
                orderMapper.updateByPrimaryKeySelective(param);
                //cipCode修改成有效
                updateVipCodes(o.getVipCode());
            }
        }
    }

    @Override
    public void syncWeixinOrder() {
        //获取所有订单为已经支付的
        TOrder order = new TOrder();
        order.setOrderState(2);
        List<TOrder> orders = orderMapper.selectListByWhere(order);

        OrderSync sync;
        if (orders != null) {
            for (TOrder o : orders) {
                sync = new OrderSync();
                sync.setAppid(Configure.getAppID());
                sync.setMch_id(Configure.getMch_id());
                sync.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
                sync.setSign_type("MD5");
                try {
                    log.info("===>同步微信订单 order = " + o.toString());
                    if (!StringUtils.isEmpty(o.getThirdOederCode())) {
                        sync.setTransaction_id(o.getThirdOederCode());
                    } else {
                        sync.setOut_trade_no(o.getOrderCode());
                    }
                    //生成签名
                    String sign = Signature.getSign(sync);
                    sync.setSign(sign);
                    String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/orderquery", sync);
                    log.info("---------订单返回:" + result);
                    checkWeiXinOrder(result);
                } catch (Exception e) {
                    log.error("同步订单失败 orderCode = " + o.getOrderCode(), e);
                }
            }
        }
    }

    /**
     * 处理微信订单
     */
    @Transactional
    public void checkWeiXinOrder(String result) {
        XStream xStream = new XStream();
        xStream.alias("xml", OrderQuery.class);
        OrderQuery resultJson = (OrderQuery) xStream.fromXML(result);
        if ("SUCCESS".equals(resultJson.getResult_code()) && "SUCCESS".equals(resultJson.getReturn_code())) {
            TOrder param = new TOrder();
            param.setOrderCode(resultJson.getOut_trade_no());
            TOrder orders = orderMapper.selectByWhere(param);
            if (orders != null && orders.getOrderState() != 4) {
                TOrder update = new TOrder();
                update.setId(orders.getId());
                update.setThirdOederCode(resultJson.getTransaction_id());
                //交易关闭
                if (resultJson.getTrade_state().equals(TradeState.CLOSED.getCode()) || resultJson.getTrade_state().equals(TradeState.PAYERROR.getCode())
                        || resultJson.getTrade_state().equals(TradeState.REVOKED.getCode())|| resultJson.getTrade_state().equals(TradeState.REFUND.getCode())) {
                    update.setOrderState(4);
                    update.setVipState(0);
                    orderMapper.updateByPrimaryKeySelective(update);
                    updateVipCodes(orders.getVipCode());
                } else if (resultJson.getTrade_state().equals(TradeState.SUCCESS.getCode())) {
                    update.setOrderState(3);
                    orderMapper.updateByPrimaryKeySelective(update);
                }
            }
        }

    }

    /**
     * 释放vip
     * @param vipCodes
     */
    private void updateVipCodes(String vipCodes) {
        TVipCodes update = new TVipCodes();
        TVipCodes codeParam = new TVipCodes();
        codeParam.setVipCode(vipCodes);
        TVipCodes codes = vipCodesMapper.selectByWhere(codeParam);
        if (codes != null) {
            update.setId(codes.getId());
            update.setVipState(1);
            vipCodesMapper.updateByPrimaryKeySelective(update);
        }
    }
    @Override
    public Earnings getEarnings(TokenBean token) {
        BigDecimal unEarnings = orderMapper.getUnEarnings(token.getMerchantId());
        if(unEarnings == null){
            unEarnings = BigDecimal.ZERO;
        }
        TMerchant po = new TMerchant();
        po.setMenchantId(token.getMerchantId());
        TMerchant merchant = merchantMapper.selectByWhere(po);
        unEarnings = unEarnings.multiply(merchant.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        Earnings earnings = new Earnings();
        earnings.setUnEarning(unEarnings);
        BigDecimal earning = settleAccountMapper.getEarnings(token.getMerchantId());
        if(earning == null){
            earning = BigDecimal.ZERO;
        }
        earnings.setEarning(earning);
        return earnings;
    }

}