package com.video.task;

import com.video.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-06
 * @Description:
 */
@Component
public class OrderTask {
    private static Logger log = LoggerFactory.getLogger(OrderTask.class);
    @Autowired
    OrderService orderService;
    /**
     * 未支付取消
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void unPayTask(){
        log.info("------未支付取消定时开始----");
        orderService.unPayTask();
        log.info("------未支付取消定时结束----");
    }
}