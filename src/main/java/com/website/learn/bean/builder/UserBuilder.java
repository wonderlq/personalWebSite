package com.website.learn.bean.builder;

import com.website.learn.bean.bo.UserDetail;
import com.website.learn.bean.bo.UserInfo;
import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.po.UserBasePo;
import com.website.learn.bean.po.UserInfoPo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-20 00:54
 */
public class UserBuilder {

    public static UserDetail userInfoPo2UserDetail(UserBasePo userBasePo){
        UserDetail userDetail = new UserDetail();
        userDetail.setBirthDay(userBasePo.getBirthDay());
        userDetail.setEmail(userBasePo.getEmail());
        userDetail.setUserName(userBasePo.getName());
        userDetail.setId(userBasePo.getId());
        return userDetail;
    }

    public static UserInfoPo buildUserInfo(UserRegisterInfo registerInfo) {
        UserInfoPo userInfo = new UserInfoPo();
        userInfo.setName(registerInfo.getName());
        userInfo.setPassword(registerInfo.getPassword());
        return userInfo;
    }

    public static UserBasePo buildUserBase(UserRegisterInfo registerInfo) {
        return null;
    }
}
