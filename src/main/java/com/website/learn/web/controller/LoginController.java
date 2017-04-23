package com.website.learn.web.controller;

import com.website.learn.bean.bo.LoginInfo;
import com.website.learn.bean.bo.UserInfo;
import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.builder.UserBuilder;
import com.website.learn.constant.ValueConstant;
import com.website.learn.service.LoginService;
import com.website.learn.service.SecurityService;
import com.website.learn.service.UserService;
import com.website.learn.util.AESUtil;
import com.website.learn.web.controller.base.BaseController;
import com.website.learn.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;

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
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    SecurityService securityService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    /***
     * 登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/signIn")
    public JsonResult login(LoginInfo loginInfo) {
        if (!checkArgument(loginInfo)) {
            return new JsonResult(3, "wrong userName or passWord");
        }

        try {
            //解密数据
            UserInfo userInfo = securityService.decoded(loginInfo);

            //验证用户名和密码
            long userId = loginService.login(userInfo);
            if (userId != ValueConstant.ERROR) {
                //获取用户基本信息
                //UserInfoPo userInfoPo = userService.findProfileById(userId);//// TODO: 2017/4/23

                //如果验证成功就将用户信息写入cookie中
                Cookie passwordCookie =
                        new Cookie(ValueConstant.COOKIE_USER_INFO, buildCookieStr(userId, userInfo));
                passwordCookie.setMaxAge(ValueConstant.COOKIE_LIFE);
                passwordCookie.setPath(ValueConstant.COOKIE_PATH);
                response.addCookie(passwordCookie);

                return new JsonResult(0, "true");
            }
        } catch (Exception e) {
            logger.info("sign in error! {}", e);
            return new JsonResult(2, "service error!");
        }
        return new JsonResult(1, "userName or password error!");
    }


    /**
     * 注册
     */
    @ResponseBody
    @RequestMapping("/signUp")
    public JsonResult signUp(UserRegisterInfo registerInfo) {
        logger.info("a new user want sign up ^_^ ");
        if(!checkArgument(registerInfo))
            return new JsonResult(1,"error sign up params");

        if(userService.signUp(registerInfo)){
            return new JsonResult(0,"Congratulations! sign up success!");
        }else {
            return new JsonResult(1,"oh oh, sign up failed, server error");
        }
    }



    private String buildCookieStr(long userId, UserInfo userInfo) {
        String cookieStr =
                new StringBuilder().append(userId).append("-").append(System.currentTimeMillis()).toString();
        return AESUtil.encrypt(cookieStr, ValueConstant.COOKIE_PASSWORD);
    }

    private boolean checkArgument(LoginInfo loginInfo) {
        if ((loginInfo == null) ||
                StringUtils.isEmpty(loginInfo.getTime()) ||
                StringUtils.isEmpty(loginInfo.getPublicKey()) ||
                StringUtils.isEmpty(loginInfo.getPassword()) ||
                StringUtils.isEmpty(loginInfo.getUserName())) {
            return false;
        }
        return true;
    }

    private boolean checkArgument(UserRegisterInfo registerInfo) {
        if ((registerInfo == null) ||
                StringUtils.isEmpty(registerInfo.getAddress()) ||
                StringUtils.isEmpty(registerInfo.getBirthDay()) ||
                StringUtils.isEmpty(registerInfo.getCountry()) ||
                StringUtils.isEmpty(registerInfo.getEmail()) ||
                StringUtils.isEmpty(registerInfo.getFrom()) ||
                StringUtils.isEmpty(registerInfo.getPassword()) ||
                StringUtils.isEmpty(registerInfo.getPhoneNum())) {
            return false;
        }
        return true;
    }
}
