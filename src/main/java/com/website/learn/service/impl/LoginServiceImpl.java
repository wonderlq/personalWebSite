package com.website.learn.service.impl;

import com.website.learn.bean.bo.UserInfo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.dao.UserDao;
import com.website.learn.service.LoginService;
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
    UserDao userDao;

    @Override
    public long login(UserInfo userInfo) {
        try {
            //数据库中存储的是md5加密过
            String password = Md5Util.md5(userInfo.getPw());
            return userDao.exist(userInfo.getName(), password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ValueConstant.ERROR;
    }
}
