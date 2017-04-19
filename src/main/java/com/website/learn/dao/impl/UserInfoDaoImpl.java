package com.website.learn.dao.impl;

import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.dao.UserInfoDao;
import com.website.learn.dao.dal.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:58
 */
@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public long exist(String name, String password) {
        UserInfoPo userInfoPo = userInfoMapper.exist(name,password);
        if (userInfoPo != null){
            return userInfoPo.getId();
        }
        return ValueConstant.ERROR;
    }

    @Override
    public void saveNewUser(UserInfoPo userInfoPo) {
        userInfoMapper.saveNewUser(userInfoPo);
    }
}
