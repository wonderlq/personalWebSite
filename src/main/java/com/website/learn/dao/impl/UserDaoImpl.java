package com.website.learn.dao.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.website.learn.bean.bo.UserRegisterInfo;
import com.website.learn.bean.builder.UserBuilder;
import com.website.learn.bean.po.UserBasePo;
import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.dao.UserDao;
import com.website.learn.dao.dal.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:58
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public long exist(String name, String password) {
        UserInfoPo userInfoPo = userInfoMapper.exist(name, password);
        if (userInfoPo != null) {
            return userInfoPo.getId();
        }
        return ValueConstant.ERROR;
    }

    @Override
    @Transactional
    public long insertNewUser(UserRegisterInfo registerInfo) {
        try {
            UserInfoPo userInfoPo = UserBuilder.buildUserInfo(registerInfo);
            long id = userInfoMapper.saveNewUser(userInfoPo);
            if (id > 0) {
                UserBasePo userBasePo = UserBuilder.buildUserBase(registerInfo);
                userInfoMapper.saveNewUserBase(userBasePo);
            }
            return  id;
        } catch (Exception e) {
            logger.info("save new user exception!",e);
            return 0;
        }
    }
}
