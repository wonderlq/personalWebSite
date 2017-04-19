package com.website.learn.bean.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 22:33
 */
@Getter
@Setter
public class UserInfo {
    private int id;
    private String pw;
    private String name;
    private String from;

    public UserInfo(String pw, String name,String from) {
        this.pw = pw;
        this.name = name;
        this.from=from;
    }

    public UserInfo(String pw, String name) {
        this.pw = pw;
        this.name = name;
    }

    public UserInfo() {
    }
}
