package com.website.learn.dao.impl;

import com.website.learn.bean.po.UserInfoPo;
import com.website.learn.dao.UserInfoDao;
import com.website.learn.dao.dal.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:58
 */
public class UsereInfoDaoImpl implements UserInfoDao {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public Boolean exist(String name, String password) {
        UserInfoPo userInfoPo = userInfoMapper.exist(name,password);
        if (userInfoPo != null){
            return true;
        }
        return false;
    }

    @Override
    public void saveNewUser(UserInfoPo userInfoPo) {
        userInfoMapper.saveNewUser(userInfoPo);
    }
}
