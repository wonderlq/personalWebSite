package com.website.learn.web.controller.base;


import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一处理异常
 * @author dell
 * @since 1.0.0
 * Created On 2017-02-20 00:40
 */
public abstract class BaseController {
    /**
     * logger
     */
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 请求
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * 响应
     */
    @Autowired
    protected HttpServletResponse response;
    /*@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        LOGGER.info("#### exception url = {} ####", request.getRequestURI());
        return new JsonResult(JsonResult.ResultCode.FAILED, e.getMessage());
    }*/

}
