package com.website.learn.bean.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-23 17:51
 */
@Getter
@Setter
public class UserBasePo {
    private long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 邮件
     */
    private String email;
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 生日
     */
    private Date birthDay;
    /**
     * 地址
     */
    private String address;
    /**
     * 国家
     */
    private String country;

}
