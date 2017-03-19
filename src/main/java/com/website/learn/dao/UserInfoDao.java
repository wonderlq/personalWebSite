package com.website.learn.dao;

import com.website.learn.bean.po.UserInfoPo;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:57
 */
@Repository
public interface UserInfoDao {
    Boolean exist(String name, String password);

    void saveNewUser(UserInfoPo userInfoPo);
}
