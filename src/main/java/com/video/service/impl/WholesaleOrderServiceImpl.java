package com.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.video.common.Configure;
import com.video.common.HttpRequest;
import com.video.common.RandomStringGenerator;
import com.video.common.Signature;
import com.video.dao.ITUserMapper;
import com.video.dao.ITWholesaleOrderMapper;
import com.video.enumUtil.ApiEnum;
import com.video.model.TUser;
import com.video.model.TWholesaleOrder;
import com.video.model.ao.OrderInfo;
import com.video.model.ao.OrderReturnInfo;
import com.video.model.ao.PayResultInfo;
import com.video.service.WholesaleOrderService;
import com.video.service.WholesalePriceService;
import com.video.util.ApiResponse;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-21
 * @Description:
 */
@Service
public class WholesaleOrderServiceImpl implements WholesaleOrderService {
    private static Logger log = LoggerFactory.getLogger(WholesaleOrderServiceImpl.class);

    @Autowired
    private WholesalePriceService wholesalePriceService;
    @Autowired
    private ITWholesaleOrderMapper wholesaleOrderMapper;
    @Autowired
    private ITUserMapper userMapper;
    @Override
    public ApiResponse subOrder(String[] numbers, BigDecimal totalPrice) {
        JSONObject json = new JSONObject();
        try {
            TokenBean token = TokenUtil.getToken();
            log.info("---------采购 token : " + token);
            if (token == null) {
                return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
            }
            log.info("---------采购 openid : " + token.getOpenId());
            if (StringUtils.isEmpty(token.getOpenId())) {
                return ApiResponse.fail(ApiEnum.OPENID_NULL);
            }
            log.info("---------采购 merchantId : " + token.getMerchantId());
            if (StringUtils.isEmpty(token.getMerchantId())) {
                return ApiResponse.fail(ApiEnum.NOT_MERCHANT);
            }
            //检查用户是否为系统用户
            TUser userParam = new TUser();
            userParam.setOpenId(token.getOpenId());
            userParam.setMenchantId(token.getMerchantId());
            userParam.setUserType(1);
            TUser user = userMapper.selectByWhere(userParam);
            if(user == null){
                return ApiResponse.fail(ApiEnum.TOKEN_ERROR);
            }
            //获取vip价格
            Map<String, Object> countPrice = wholesalePriceService.getCountPrice(numbers);
            if(countPrice == null || countPrice.size() <=0) {
                log.info("---------采购 获取vip价格失败 ");
                return ApiResponse.fail(ApiEnum.SUB_ORDER_ERROR);
            }
            if(totalPrice.compareTo((BigDecimal) countPrice.get("totalPrice")) != 0){
                log.info("---------采购 价格不一致");
                return ApiResponse.fail(ApiEnum.SUB_ORDER_ERROR);
            }
            OrderInfo orderInfo = subToWiXinAo(totalPrice, token.getOpenId());
            int succ = addOrder(orderInfo, token.getMerchantId(), countPrice);
            if(succ <= 0){
                return ApiResponse.fail(ApiEnum.SUB_ORDER_ERROR);
            }
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", orderInfo);
            log.info("---------采购订单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && "SUCCESS".equals(returnInfo.getResult_code())) {
                json.put("prepayId", returnInfo.getPrepay_id());
                updateOrder(orderInfo.getOut_trade_no(), returnInfo.getPrepay_id());
            } else {
                log.error(returnInfo.getReturn_msg());
                return ApiResponse.fail(ApiEnum.SUB_ORDER_ERROR);
            }
        }catch (Exception e){
            log.error("批发异常",e);
        }
        return ApiResponse.success(json.toJSONString());
    }

    private OrderInfo subToWiXinAo(BigDecimal vipPrice,String openid) throws IllegalAccessException {
        OrderInfo order = new OrderInfo();
        order.setAppid(Configure.getAppID());
        order.setMch_id(Configure.getMch_id());
        order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        order.setBody("影视会员VIP" +vipPrice);
        order.setOut_trade_no(RandomStringGenerator.getRandomStringByLength(20));
        order.setTotal_fee(vipPrice.multiply(BigDecimal.valueOf(100)).setScale(0));
        order.setSpbill_create_ip(Configure.getSpbill_create_ip());
        order.setNotify_url("https://www.filmunion.com.cn/video/payNotify/payResult");
        order.setTrade_type("JSAPI");
        order.setOpenid(openid);
        order.setSign_type("MD5");
        //生成签名
        String sign = Signature.getSign(order);
        order.setSign(sign);
        return order;
    }

    private int addOrder(OrderInfo order,String merchantId,Map<String, Object> countPrice){
        TWholesaleOrder wholesaleOrder = new TWholesaleOrder();
        wholesaleOrder.setMerchantId(merchantId);
        wholesaleOrder.setOpenId(order.getOpenid());
        wholesaleOrder.setOrderCode(order.getOut_trade_no());
        wholesaleOrder.setOrderDesc(countPrice.get("list").toString());
        wholesaleOrder.setOrderPrice(order.getTotal_fee());
        wholesaleOrder.setOrderState(1);
        wholesaleOrder.setCreateTime(new Date());
        List<TWholesaleOrder> list  = new ArrayList();
        list.add(wholesaleOrder);
        return wholesaleOrderMapper.insertBatch(list);
    }

    private void updateOrder(String orderCode,String prePayId){
        TWholesaleOrder param = new TWholesaleOrder();
        param.setOrderCode(orderCode);
        TWholesaleOrder wholesaleOrder = wholesaleOrderMapper.selectByClassElement(param);
        if(wholesaleOrder != null){
            wholesaleOrder.setPrepayId(prePayId);
            wholesaleOrderMapper.updateByPrimaryKeySelective(wholesaleOrder);
        }
    }
    @Override
    public void unSuccessOrder(PayResultInfo order) {
        TWholesaleOrder param = new TWholesaleOrder();
        param.setOrderCode(order.getOut_trade_no());
        param.setOrderState(1);
        TWholesaleOrder orderResult = wholesaleOrderMapper.selectByClassElement(param);
        if (orderResult != null) {
            TWholesaleOrder update = new TWholesaleOrder();
            update.setId(orderResult.getId());
            update.setOrderState(2);
            wholesaleOrderMapper.updateByPrimaryKeySelective(update);
            return;
        }
    }

    @Override
    public boolean successOrder(PayResultInfo param) {
        TWholesaleOrder order = new TWholesaleOrder();
        order.setOrderCode(param.getOut_trade_no());
        TWholesaleOrder orderResult = wholesaleOrderMapper.selectByClassElement(order);
        if (orderResult != null && orderResult.getOrderState() <= 2) {
            TWholesaleOrder update = new TWholesaleOrder();
            update.setId(orderResult.getId());
            update.setOrderState(3);
            update.setThirdOrderCode(param.getTransaction_id());
            wholesaleOrderMapper.updateByPrimaryKeySelective(update);
            return true;
        }
        return false;
    }
}