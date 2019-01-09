package com.video.service;


import com.video.model.TUser;
import com.video.util.TokenBean;

import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public interface UserService {

    void addUserInfo(TokenBean tokenBean);
    void updateUserInfo(TokenBean tokenBean);
    List<TUser> getTuser(String openId);
    List<TUser> findUser(TUser user);

}