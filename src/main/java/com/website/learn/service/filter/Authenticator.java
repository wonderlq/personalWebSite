package com.website.learn.service.filter;

import com.website.learn.bean.bo.UserDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-27 22:27
 */
public interface Authenticator {
    UserDetail authenticate(HttpServletRequest request, HttpServletResponse response);
}
