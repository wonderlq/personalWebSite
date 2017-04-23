package com.website.learn.dao;

import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.po.UserInfoPo;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:57
 */
public interface UserDao {
    long exist(String name, String password);

    long insertNewUser(UserRegisterInfo userInfoPo);
}
