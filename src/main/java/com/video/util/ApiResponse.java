package com.video.util;

import com.alibaba.fastjson.JSON;
import com.video.enumUtil.ApiEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: liujianqiang
 * @Date: 2019-01-01
 * @Description:
 */
public class ApiResponse <D> implements Serializable {
    private static Logger log = LoggerFactory.getLogger(ApiResponse.class);

    /**
     * 错误号(0成功)
     */
    int errCode;

    /**
     * 返回数据
     */
    D data;

    /**
     * 错误信息
     */
    String errMsg;



    private ApiResponse(int errCode, D data, String errMsg) {
        this.errCode = errCode;
        this.data = data;
        this.errMsg = errMsg;
    }

    private static <D> ApiResponse newInstance(int errCode, D data, String errMsg) {
        ApiResponse apiResponse = new ApiResponse<>(errCode, data, errMsg);
        log.info("返回数据:{}", JSON.toJSONString(apiResponse));
        return apiResponse;
    }

    private static <E extends Enum<E>, D> ApiResponse<D> fail(D data, E error) {

        int code = ApiEnum.SYSTEM_ERROR.getErrCode();
        String msg =ApiEnum.SYSTEM_ERROR.getErrMsg();
        Class clazz = error.getDeclaringClass();
        try {
            for (Object obj : clazz.getEnumConstants()) {
                if (obj.equals(error)) {
                    code = (int) clazz.getMethod("getErrCode", new Class[0]).invoke(obj);
                    msg = (String) clazz.getMethod("getErrMsg", new Class[0]).invoke(obj);
                    if (code < 0) {
                        return newInstance(code, data, msg);
                    } else {
                        return newInstance(code, data, msg);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("组装返回数据异常", e);
        } catch (InvocationTargetException e) {
            log.error("组装返回数据异常", e);
        } catch (NoSuchMethodException e) {
            log.error("组装返回数据异常", e);
        }

        return newInstance(code, data, msg);

    }

    public static final ApiResponse success() {
        return success(null);
    }

    public static final <D> ApiResponse<D> success(D data) {
        return newInstance(0, data, null);
    }

    public static final <E extends Enum<E>, D> ApiResponse<D> fail(E error) {
        return fail(null, error);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}