package com.website.learn.bean.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:33
 */
@Getter
@Setter
public class UserInfoPo {
    private int id;
    private String password;
    private String name;
    private Date createdTime;
    private Date modifyTime;
    private Integer gender;
    private String email;
    private String phoneNum;

    public UserInfoPo(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public UserInfoPo() {
    }
}
