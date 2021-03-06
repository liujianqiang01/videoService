package com.video.interceptors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
       /* registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login/**").excludePathPatterns("/payNotify/**").excludePathPatterns("/getVipType")
        .excludePathPatterns("/html/**");*/

        registry.addInterceptor(tokenInterceptor).addPathPatterns("/order/**").addPathPatterns("/getPayForm").addPathPatterns("/merchant/**")
                .addPathPatterns("/wholesaleOrder/**").addPathPatterns("/getMVipType").addPathPatterns("/getVipType").addPathPatterns("/login/apply");
    }

}