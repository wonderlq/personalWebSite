package com.website.learn.bean.bo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 02:17
 */
public class LoginInfo {
    private String time;

    private String userName;

    private String password;

    private String publicKey;

    /**
     * 来源标志量
     */
    private String from;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
