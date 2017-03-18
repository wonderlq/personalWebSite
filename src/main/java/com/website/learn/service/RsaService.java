package com.website.learn.service;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-16 00:09
 */
public interface RsaService {

    /**
     * 获取公钥
     * @return
     * @param time
     */
    String createPublicKey(String time);

    /**
     * 解密
     * @param txt
     * @return
     */
    String decoded(String txt);
}
