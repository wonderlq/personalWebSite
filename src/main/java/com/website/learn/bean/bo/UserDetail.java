package com.website.learn.bean.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-27 22:28
 */
@Getter
@Setter
public class UserDetail {
    private String userName;
    private String email;
    private Date birthDay;
    private long id;
}
