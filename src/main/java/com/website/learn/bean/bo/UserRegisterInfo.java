package com.website.learn.bean.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-04-23 17:28
 */
@Getter
@Setter
public class UserRegisterInfo {
    /**
     * 密码
     */
    private String password;
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

    //---其他--//
    /**
     * 来源方式，pc = "PC"？phone = "PHONE"？other = "OTHER"
     */
    private String from;
}
