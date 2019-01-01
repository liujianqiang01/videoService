package com.video.interceptors;


import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * token拦截器
 */
@Service
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String path = request.getServletPath();
        log.info("path:"+path+"<br>");
        if (!StringUtils.isEmpty(request.getHeader("token"))) {
            String token = request.getHeader("token");
            TokenBean tokenBean = (TokenBean) request.getSession().getAttribute(token);
            if(tokenBean == null){
               response.setStatus(-1);
               log.error("token无效");
               return false;
           }
            //将 token 放入线程本地变量
            TokenUtil.setHeader(tokenBean);
            return true;
        } else {
            response.setStatus(-2);
            log.error("header未携带token");
           return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        //清除token信息
        TokenUtil.removeToken();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

}
