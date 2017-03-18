package com.website.learn.service.impl;

import com.website.learn.bean.po.KeyPairPo;
import com.website.learn.dao.KeyDao;
import com.website.learn.service.RsaService;
import com.website.learn.util.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Map;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-16 00:09
 */
@Service
public class RsaServiceImpl implements RsaService {

    @Autowired
    KeyDao keyDao;

    @Override
    public String createPublicKey(String time) {
        //redis获取
        KeyPairPo keyPairPoCache = keyDao.getKey(time);

        String publicKeyBase64;
        String privateKeyBase64;
        if (keyPairPoCache == null) {
            //生成公钥私钥
            Map<String, byte[]> keyMap = RsaUtil.generateKeyBytes();
            publicKeyBase64 = Base64Utils.encodeToString(keyMap.get(RsaUtil.PUBLIC_KEY));
            privateKeyBase64 = Base64Utils.encodeToString(keyMap.get(RsaUtil.PRIVATE_KEY));

            keyPairPoCache =
                    new KeyPairPo.Builder().setPrivateKey(privateKeyBase64).setPublicKey(publicKeyBase64).build();
            //公钥私钥存储到redis
            keyDao.saveKeyPairs(time, keyPairPoCache);
            return publicKeyBase64;
        } else {
            return keyPairPoCache.getPublicKey();
        }
    }

    @Override
    public String decoded(String txt) {

        return null;
    }
}
