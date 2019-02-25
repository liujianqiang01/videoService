package com.video.controller;

import com.video.enumUtil.ApiEnum;
import com.video.model.TMerchant;
import com.video.model.TMerchantPrice;
import com.video.model.TUser;
import com.video.service.MerchantService;
import com.video.service.UserService;
import com.video.util.ApiResponse;
import com.video.util.Base64ToImage;
import com.video.util.TokenBean;
import com.video.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-09
 * @Description:
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController {
    private static Logger log = LoggerFactory.getLogger(MerchantController.class);
    @Autowired
    MerchantService merchantService;
    @Autowired
    UserService userService;

    /**
     * 补充商户信息
     *
     * @param request
     * @param merchant
     * @return
     */
    @PostMapping("/saveMerchant")
    @ResponseBody
    public ApiResponse addMerchantInfo(HttpServletRequest request, TMerchant merchant) {
        TokenBean tokenBean = TokenUtil.getToken();
        TUser user = new TUser();
        user.setUserType(1);
        user.setMenchantId(tokenBean.getMerchantId());
        user.setOpenId(tokenBean.getOpenId());
        List<TUser> userMer = userService.findUser(user);
        if (userMer != null && userMer.size() > 0) {
            TMerchant merchantParam = new TMerchant();
            merchantParam.setMenchantId(tokenBean.getMerchantId());
            TMerchant merchantResult = merchantService.selectByWhere(merchantParam);
            if (merchantResult != null) {
                merchant.setId(merchantResult.getId());
                merchantService.updateByPrimaryKeySelective(merchant);
            } else {
                List<TMerchant> merchants = new ArrayList<>();
                merchants.add(merchant);
                merchantService.insertBatch(merchants);
            }
            return ApiResponse.success();
        }
        return ApiResponse.fail(ApiEnum.RETURN_ERROR);
    }

    @PostMapping("/getMerchant")
    @ResponseBody
    public ApiResponse addMerchantInfo() {
        TokenBean tokenBean = TokenUtil.getToken();
        TMerchant merchantp = new TMerchant();
        merchantp.setMenchantId(tokenBean.getMerchantId());
        TMerchant merchant = merchantService.selectByWhere(merchantp);
        if (merchant != null) {
            return ApiResponse.success(merchant);
        } else {
            return ApiResponse.fail(ApiEnum.RESULT_NULL);
        }
    }

    @PostMapping("/applyPrice")
    @ResponseBody
    public ApiResponse applyPrice( TMerchantPrice merchantPrice) {
        TokenBean tokenBean = TokenUtil.getToken();
        TUser user = new TUser();
        user.setUserType(1);
        user.setMenchantId(tokenBean.getMerchantId());
        List<TUser> userMer = userService.findUser(user);
        if (userMer != null && userMer.size() > 0) {
            merchantPrice.setCreateTime(new Date());
            merchantPrice.setMerchanId(tokenBean.getMerchantId());
            merchantPrice.setState(0);
            log.info("价格调整申请入参："+merchantPrice.toString());
            userService.applyPrice(merchantPrice);
            return ApiResponse.success();
        }
        return ApiResponse.fail(ApiEnum.RETURN_ERROR);
    }

    @PostMapping("/saveImage")
    @ResponseBody
    public ApiResponse saveImage(String image) {
        TokenBean tokenBean = TokenUtil.getToken();
        //String path="/home/tomcat/apache-tomcat-8.5.37/webapps/video/image/erweima/"+ tokenBean.getMerchantId() +".jpg";
        String path="/Users/liujianqiang/Desktop/project/videoService/src/main/webapp/image/erweima/"+ tokenBean.getMerchantId() +".jpg";
        boolean b = Base64ToImage.generateImage(image, path);
        if(b){
          return ApiResponse.success(tokenBean.getMerchantId()+".jpg");
        }
        return ApiResponse.fail(ApiEnum.RETURN_ERROR);
    }

}