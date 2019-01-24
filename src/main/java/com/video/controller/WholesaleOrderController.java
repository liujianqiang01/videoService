package com.video.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.video.enumUtil.ApiEnum;
import com.video.model.TVipCodes;
import com.video.service.VipCodesService;
import com.video.service.WholesaleOrderService;
import com.video.util.ApiResponse;
import com.video.util.ExcelUtil;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-21
 * @Description:
 */
@Controller
@RequestMapping("/wholesaleOrder")
public class WholesaleOrderController {
    private static Logger log = LoggerFactory.getLogger(WholesaleOrderController.class);

    @Autowired
    WholesaleOrderService wholesaleOrderService;
    @Autowired
    VipCodesService vipCodesService;
    @PostMapping("/subOrder")
    @ResponseBody
    public ApiResponse subOrder(String[] number, BigDecimal totalPrice){
        if(number == null || number.length <= 0 || totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) <= 0){
            return ApiResponse.fail(ApiEnum.PARAM_NULL);
        }
        return ApiResponse.success(wholesaleOrderService.subOrder(number,totalPrice));
    }


    /**
     * Excel表格导出接口
     * @param response response对象
     * @throws IOException 抛IO异常
     */
    @GetMapping("excelDownload")
    public ApiResponse excelDownload(HttpServletResponse response, HttpServletRequest request) throws IOException {
        TokenBean tokenBean = TokenUtil.getToken();
        String vipType = request.getParameter("vipType");
        String number = request.getParameter("number");
        if(StringUtils.isEmpty(tokenBean.getMerchantId())){
            return ApiResponse.fail(ApiEnum.PARAM_ERROR);
        }
        log.info("=====>type= " + vipType+",number =" + number);
        TVipCodes param = new TVipCodes();
        param.setMerchantId(tokenBean.getMerchantId());
        param.setVipType(Integer.valueOf(vipType));
        param.setVipState(1);
        PageInfo<TVipCodes> page = vipCodesService.findPage(0, Integer.valueOf(number), param);
        List<TVipCodes> list = page.getList();
        if(list == null || list.size()<= 0 ){
            return ApiResponse.fail(ApiEnum.PARAM_ERROR);
        }
        List<String[]> listStr = new ArrayList<>();
        String[] strArray;
        for(TVipCodes codes : list){
            strArray = new String[1];
            strArray[0] = codes.getVipCode();
            listStr.add(strArray);
        }
        String title = "月卡";
        if("2".equals(vipType)){
            title = "季卡";
        }else  if("3".equals(vipType)){
            title = "年卡";
        }
        String[] titles ={title};
        String fileName = String.valueOf(System.currentTimeMillis());
        ExcelUtil.generateExcel(response,fileName,titles,listStr);
        log.info("====>会员卡导出成功 ！");
        return ApiResponse.success();
    }
}