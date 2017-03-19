package com.website.learn.dao;

import com.website.learn.bean.po.KeyPairPo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 01:00
 */
public interface KeyDao {

    /**
     * 保存秘钥到缓存
     * @param time
     * @param keyPairPo
     */
    void saveKeyPairs(String time, KeyPairPo keyPairPo);

    KeyPairPo getKey(String time);

}
