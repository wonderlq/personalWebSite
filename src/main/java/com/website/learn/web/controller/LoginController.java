package com.website.learn.web.controller;

import com.website.learn.bean.bo.LoginInfo;
import com.website.learn.bean.bo.UserInfo;
import com.website.learn.service.LoginService;
import com.website.learn.service.SecurityService;
import com.website.learn.web.controller.base.BaseController;
import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录相关
 *
 * @author dell
 * @since 1.0.0
 * Created On 2017-02-20 00:58
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SecurityService securityService;

    @Autowired
    LoginService loginService;

    /***
     * 登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/signIn")
    public JsonResult login(LoginInfo loginInfo) {
        checkArgument(loginInfo);
        try {
            //解密数据
            UserInfo userInfo = securityService.decoded(loginInfo);
            //验证用户名和密码
            boolean isExist = loginService.login(userInfo);
            if (isExist){
                return new JsonResult(0,"true");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(1,"false");
    }



    private void checkArgument(LoginInfo loginInfo) {
        if ((loginInfo == null) ||
                StringUtils.isEmpty(loginInfo.getTime()) ||
                StringUtils.isEmpty(loginInfo.getPublicKey()) ||
                StringUtils.isEmpty(loginInfo.getPassword()) ||
                StringUtils.isEmpty(loginInfo.getUserName())) {
            throw new IllegalArgumentException();
        }
    }
}
