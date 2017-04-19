package com.website.learn.service.filter;

import com.website.learn.bean.bo.UserDetail;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * basic认证
 *
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-27 22:50
 */
@Service
public class BaseAuthenticator implements Authenticator {

    @Override
    public UserDetail authenticate(HttpServletRequest request, HttpServletResponse response) {
        String auth = getHeaderFromRequest(request, "Authorization");
        if (auth == null) {
            return null;
        }
        String username = parseUsernameFromAuthorizationHeader(auth);
        String password = parsePasswordFromAuthorizationHeader(auth);
        return authenticateUserByPassword(username, password);
    }

    private String parsePasswordFromAuthorizationHeader(String auth) {
        return "";
    }

    private String parseUsernameFromAuthorizationHeader(String auth) {
        return "";
    }

    private UserDetail authenticateUserByPassword(String username, String password) {
        //依据相应规则解析数据，获取相应的用户信息
        return null;
    }


    private String getHeaderFromRequest(HttpServletRequest request, String authorization) {
        return request.getHeader(authorization);
    }
}
