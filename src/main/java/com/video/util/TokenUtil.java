package com.video.util;

import com.video.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: liujianqiang
 * @Date: 2018-12-31
 * @Description:
 */
public class TokenUtil {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    static final ThreadLocal<TokenBean> threadLocal = ThreadLocal.withInitial(TokenBean::new);

    public static void setHeader(TokenBean tokenBean) {
       threadLocal.set(tokenBean);
    }

    public static TokenBean getToken() {
        return threadLocal.get();
    }

    public static void removeToken() {
        threadLocal.remove();
    }

}