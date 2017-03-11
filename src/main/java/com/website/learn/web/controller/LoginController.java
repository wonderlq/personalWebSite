package com.website.learn.web.controller;

import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录相关
 * @author dell
 * @since 1.0.0
 * Created On 2017-02-20 00:58
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {


    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /***
     * 登录
     * @return
     */
    @ResponseBody
    @RequestMapping("/signIn")
    public JsonResult login(HttpServletRequest request, HttpServletResponse response){


        return new JsonResult();
    }

}
