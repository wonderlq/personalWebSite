package com.website.learn.service;

import com.website.learn.bean.bo.LoginInfo;
import com.website.learn.bean.bo.UserInfo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-16 00:09
 */
public interface SecurityService {

    /**
     * 获取公钥
     * @return
     * @param time
     */
    String createPublicKey(String time);

    /**
     * 解密
     * @param txt
     * @return UserInfo 用户信息
     */
    UserInfo decoded(LoginInfo txt);

}
