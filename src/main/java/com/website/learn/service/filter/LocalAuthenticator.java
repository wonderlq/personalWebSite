package com.website.learn.service.filter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.website.learn.bean.bo.UserDetail;
import com.website.learn.bean.builder.UserBuilder;
import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie数据解析
 *
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-27 22:50
 */
@Service
public class LocalAuthenticator implements Authenticator {
    @Autowired
    UserService userService;


    @Override
    public UserDetail authenticate(HttpServletRequest request, HttpServletResponse response) {
        String userInfo = getCookie(request, ValueConstant.COOKIE_USER_INFO);
        if (StringUtils.isEmpty(userInfo)) {
            return null;
        }
        return getUserInfoByCookie(userInfo);
    }

    private UserDetail getUserInfoByCookie(String cookie) {
        ////解析cookie字符串，获取相应的用户信息
        //这个cookie里明文存储了id和name,格式“id-time”
        String[] s = cookie.split("-");
        UserInfoPo userInfoPo = userService.findProfileById(Long.valueOf(s[0]));
        return UserBuilder.userInfoPo2UserDetail(userInfoPo);
    }


    private String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cook = cookies[i];
            if (cook.getName().equalsIgnoreCase(cookieName)) { //获取键
                return cook.getValue().toString();    //获取值
            }
        }
        return ValueConstant.EMPTY_STRING;
    }
}
