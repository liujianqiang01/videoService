package com.video.service;


import com.video.util.TokenBean;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface UserService {

    void addUserInfo(TokenBean tokenBean);
    void updateUserInfo(TokenBean tokenBean);

}