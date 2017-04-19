package com.website.learn.bean.builder;

import com.website.learn.bean.bo.UserDetail;
import com.website.learn.bean.po.UserInfoPo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-20 00:54
 */
public class UserBuilder {

    public static UserDetail userInfoPo2UserDetail(UserInfoPo userInfoPo){
        UserDetail userDetail = new UserDetail();
        userDetail.setBirthDay(userInfoPo.getBirthDay());
        userDetail.setEmail(userInfoPo.getEmail());
        userDetail.setUserName(userInfoPo.getName());
        userDetail.setId(userInfoPo.getId());
        return userDetail;
    }
}
