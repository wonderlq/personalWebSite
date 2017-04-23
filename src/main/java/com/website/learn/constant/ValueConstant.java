package com.website.learn.constant;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 01:05
 */
public interface ValueConstant {

    /**
     * 公钥私钥的缓存key，值为KeyPairPo或者""
     */
    String KEY_PAIRS = "key_pair:";

    /**
     * 过期时间5分钟
     */
    int EXPIRE_TIME = 5 * 60;

    String COOKIE_USER_INFO = "USER_INFO";

    /**
     * 错误码
     */
    long ERROR = -1;
    /**
     * cookie的存活时间
     */
    int COOKIE_LIFE = 30000;
    String COOKIE_PATH = "/";
    String EMPTY_STRING = "";
    String COOKIE_PASSWORD = "merlin-no.1";
}
