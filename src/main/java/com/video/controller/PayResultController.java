package com.video.controller;

import com.thoughtworks.xstream.XStream;
import com.video.common.StreamUtil;
import com.video.model.ao.PayResultInfo;
import com.video.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 接收支付结果
 */
@Controller
@RequestMapping("/payNotify")
public class PayResultController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	OrderService orderService;
	@RequestMapping("/payResult")
	@ResponseBody
	public Object payResult(HttpServletRequest requset) throws  IOException{
		log.info("-------支付回调:");

		String result = StreamUtil.read(requset.getInputStream());
		XStream xStream = new XStream();
		log.info("-------支付回掉结果:"+xStream);
		xStream.alias("xml", PayResultInfo.class);
		PayResultInfo returnInfo = (PayResultInfo) xStream.fromXML(result);
		log.info("-------支付结果xml:"+result);
		log.info("-------支付结果bean:"+result);
		StringBuffer sb = new StringBuffer("<xml><return_code>FAIL</return_code><return_msg>处理异常</return_msg></xml>");

		if("SUCCESS".equals(returnInfo.getResult_code())&& "SUCCESS".equals(returnInfo.getReturn_code())){
			boolean b = orderService.successOrder(returnInfo);
			if(b) {
				sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
			}
		}else {
			orderService.unSuccessOrder(returnInfo);
		}
		return  sb.toString();
	}
}
