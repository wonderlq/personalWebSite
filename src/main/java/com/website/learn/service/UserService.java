package com.website.learn.service;

import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.po.UserBasePo;
import com.website.learn.bean.po.UserInfoPo;

/**
 * 用户相关，查找用户信息
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-20 00:20
 */
public interface UserService {
    /**
     * 依据id查找用户详细信息（不包括用户密码）；
     * @param userId 用户id
     * @return
     */
    UserBasePo findProfileById(long userId);

    /**
     * 注册新用户
     * @param registerInfo 注册信息
     * @return
     */
    boolean signUp(UserRegisterInfo registerInfo);

    /**
     * 查找用户是否存在
     * @param userName 用户名
     */
    boolean isExit(String userName);
}
