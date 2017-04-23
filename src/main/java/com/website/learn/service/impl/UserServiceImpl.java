package com.website.learn.service.impl;

import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.builder.UserBuilder;
import com.website.learn.bean.po.UserBasePo;
import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.dao.UserDao;
import com.website.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-23 17:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public UserBasePo findProfileById(long userId) {
        return null;
    }

    @Override
    public boolean signUp(UserRegisterInfo registerInfo) {
        long id = userDao.insertNewUser(registerInfo);
        if (id > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExit(String userName) {
        return false;
    }
}
