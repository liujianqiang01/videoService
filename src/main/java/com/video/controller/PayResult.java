package com.video.controller;

import com.video.common.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 接收支付结果
 */
@Controller
public class PayResult{
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(GetOpenIdController.class);

	@RequestMapping("/payResult")
	@ResponseBody
	public Object payResult(HttpServletRequest requset) throws  IOException{
		String reqParams = StreamUtil.read(requset.getInputStream());
		log.info("-------支付结果:"+reqParams);
		StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
		return  sb.toString();
	}

}
