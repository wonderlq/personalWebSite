package com.website.learn.service.impl;

import com.website.learn.bean.bo.UserInfo;
import com.website.learn.dao.KeyDao;
import com.website.learn.dao.UserInfoDao;
import com.website.learn.service.LoginService;
import com.website.learn.service.SecurityService;
import com.website.learn.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 02:29
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public boolean login(UserInfo userInfo) {
        try {
            //数据库中存储的是md5加密过
            String password = Md5Util.md5(userInfo.getPw());
            return userInfoDao.exist(userInfo.getName(), password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
