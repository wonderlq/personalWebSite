package com.website.learn.bean.bo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-27 21:38
 */
public class UserContext implements AutoCloseable{
    static final ThreadLocal<UserDetail> current = new ThreadLocal<>();

    public UserContext(UserDetail userDetail) {
        current.set(userDetail);
    }

    public static UserDetail getCurrentUser(){
        return current.get();
    }

    @Override
    public void close() throws Exception {
        current.remove();
    }
}
