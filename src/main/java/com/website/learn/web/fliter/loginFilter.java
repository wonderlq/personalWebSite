package com.website.learn.web.fliter;


import com.google.common.collect.Lists;
import com.website.learn.bean.bo.UserDetail;
import com.website.learn.service.filter.Authenticator;
import com.website.learn.bean.bo.UserContext;
import com.website.learn.service.filter.BaseAuthenticator;
import com.website.learn.service.filter.LocalAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-02-20 00:41
 */
@Component
public class loginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(loginFilter.class);
    @Autowired
    BaseAuthenticator baseAuth;
    @Autowired
    LocalAuthenticator cookieAuth;

    private List<String> whiteList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        whiteList = Lists.newArrayList("get_key", "/login.html","/login");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserDetail userDetail = new UserDetail();
        //白名单过滤
        if (!isExcludes(request.getRequestURI())) {
            userDetail = tryGetAuthenticatedUser(request, response);
            if(userDetail == null){
                logger.info("ha ha, catch u!");
                return;
            }
        }

        try (UserContext context = new UserContext(userDetail)) {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isExcludes(String requestURI) {
        for (String str : whiteList) {
            if (requestURI.contains(str) || requestURI.contains("/js/")
                    || requestURI.contains("/css/") || requestURI.contains("/img/")
                    || "/favicon.ico".equals(requestURI)) {
                return true;
            }
        }
        return false;
    }

    private Authenticator[] initAuthenticators() {
        return new Authenticator[] {baseAuth, cookieAuth};
    }

    private UserDetail tryGetAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) {
        Authenticator[] authenticators = initAuthenticators();

        UserDetail userDetail = null;
        for (Authenticator authenticator : authenticators) {
            userDetail = authenticator.authenticate(request, response);
            if (userDetail != null) {
                break;
            }
        }
        return userDetail;
    }

    @Override
    public void destroy() {

    }
}
