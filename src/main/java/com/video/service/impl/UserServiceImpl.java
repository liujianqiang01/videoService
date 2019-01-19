package com.video.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.video.dao.ITMerchantMapper;
import com.video.dao.ITUserMapper;
import com.video.model.TMerchant;
import com.video.model.TUser;
import com.video.service.UserService;
import com.video.util.TokenBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ITUserMapper userMapper;
    @Autowired
    ITMerchantMapper merchantMapper;
    /**
     * 无论商户还是普通用户，第一次进入先绑定微信号的关系
     * 一个商户号不允许绑定多个商户
     * 普通用户不允许绑定多个商户，并且只能绑定第一次扫码进入的商户
     * 以后每次进入修改用户昵称
     * @param tokenBean
     */
    @Override
    @Transactional
    public void addUserInfo(TokenBean tokenBean) {
        String merchantId = tokenBean.getMerchantId();
        TUser user = new TUser();
        user.setOpenId(tokenBean.getOpenId());
        user.setMenchantId(merchantId);
        //判断商户号是否绑定对应的商户
        TUser merchant = new TUser();
        merchant.setMenchantId(merchantId);
        merchant.setUserType(1);
        List<TUser> result = userMapper.selectListByWhere(merchant);
        if((result == null|| result.size() <= 0) &&
                (tokenBean.getUserType() == null ||tokenBean.getUserType() == 0)){//当商户号没有绑定商户关系并且用户不是商户
            user.setUserType(1);
        }else {
            user.setUserType(0);
        }

        //判断商户号是否存在
        TMerchant merchantParam = new TMerchant();
        merchantParam.setMenchantId(tokenBean.getMerchantId());
        TMerchant merchantResult = merchantMapper.selectByWhere(merchantParam);
        if(merchantResult == null){
            return;
        }
        //判断某个用户是否已经存在对应关系的商户号
        TUser check = new TUser();
        check.setUserType(user.getUserType());
        check.setOpenId(tokenBean.getOpenId());
        List<TUser> checkResult = userMapper.selectListByWhere(check);
        if(checkResult == null || checkResult.size() <= 0) {
            if(user.getUserType() == 1) {
                TMerchant updateMerchant = new TMerchant();
                updateMerchant.setState(1);
                updateMerchant.setId(merchantResult.getId());
                merchantMapper.updateByPrimaryKeySelective(updateMerchant);
            }
            List<TUser> paramList = new ArrayList<>();
            paramList.add(user);
            userMapper.insertBatch(paramList);
        }
        tokenBean.setUserType(user.getUserType());
    }

    @Override
    public void updateUserInfo(TokenBean tokenBean) {
        TUser user = new TUser();
        user.setOpenId(tokenBean.getOpenId());
        user.setMenchantId(tokenBean.getMerchantId());
        List<TUser> result = userMapper.selectListByWhere(user);
        if(result != null ) {
            for(TUser u : result) {
                TUser update = new TUser();
                update.setUserId(u.getUserId());
                update.setNickName(tokenBean.getNickName());
                update.setGender(tokenBean.getGender());
                update.setAvatarUrl(tokenBean.getAvatarUrl());
                userMapper.updateByPrimaryKeySelective(update);
            }
        }
    }

    @Override
    public  List<TUser> getTuser(String openId) {
        TUser user = new TUser();
        user.setOpenId(openId);
        return  userMapper.selectListByWhere(user);
    }

    @Override
    public List<TUser> findUser(TUser user) {
        return userMapper.selectListByWhere(user);
    }
}