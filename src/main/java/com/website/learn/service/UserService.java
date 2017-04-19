package com.website.learn.service;

import com.website.learn.bean.po.UserInfoPo;

/**
 * 用户相关，查找用户信息
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-20 00:20
 */
public interface UserService {
    UserInfoPo findProfileById(long userId);
}
