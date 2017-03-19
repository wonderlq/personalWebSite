package com.website.learn.dao.dal.mapper;

import com.website.learn.bean.po.UserInfoPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 23:03
 */
public interface UserInfoMapper {

    UserInfoPo exist(@Param("name") String name, @Param("password") String password);

    void saveNewUser(@Param("userInfoPo") UserInfoPo userInfoPo);
}
