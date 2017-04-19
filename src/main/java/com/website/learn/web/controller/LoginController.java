package com.website.learn.web.controller;

import com.website.learn.bean.bo.LoginInfo;
import com.website.learn.bean.bo.UserDetail;
import com.website.learn.bean.bo.UserInfo;
import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.service.LoginService;
import com.website.learn.service.SecurityService;
import com.website.learn.service.UserService;
import com.website.learn.util.Md5Util;
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
        checkArgument(loginInfo);

        try {
            //解密数据
            UserInfo userInfo = securityService.decoded(loginInfo);

            //验证用户名和密码
            long userId = loginService.login(userInfo);
            if (userId != ValueConstant.ERROR) {
                //获取用户基本信息
                //UserInfoPo userInfoPo = userService.findProfileById(userId);

                //如果验证成功就将用户信息写入cookie中
                Cookie passwordCookie =
                        new Cookie(ValueConstant.COOKIE_USER_INFO,buildCookieStr(userId,userInfo));
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

    private String buildCookieStr(long userId, UserInfo userInfo) {
        return new StringBuilder().append(userId).append("-").append(userInfo.getName()).toString();
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
