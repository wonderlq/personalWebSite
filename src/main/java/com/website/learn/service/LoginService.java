package com.website.learn.service;

import com.website.learn.bean.bo.UserInfo;

/**
 * 处理登录相关
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 02:29
 */
public interface LoginService {
    long login(UserInfo userInfo);
}
