package com.website.learn.service.impl;

import com.website.learn.bean.bo.LoginInfo;
import com.website.learn.bean.po.KeyPairPo;
import com.website.learn.bean.bo.UserInfo;
import com.website.learn.dao.KeyDao;
import com.website.learn.service.SecurityService;
import com.website.learn.util.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import java.security.PrivateKey;
import java.util.Map;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-16 00:09
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

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

            keyPairPoCache = new KeyPairPo(publicKeyBase64, privateKeyBase64);
            //公钥私钥存储到redis
            keyDao.saveKeyPairs(time, keyPairPoCache);
            return decorateString(publicKeyBase64, RsaUtil.PUBLIC_KEY);
        } else {
            return decorateString(keyPairPoCache.getPublicKey(), RsaUtil.PUBLIC_KEY);
        }
    }

    @Override
    public UserInfo decoded(LoginInfo info) {
        //依据时间获取钥匙缓存
        String cacheKey = info.getTime();
        KeyPairPo keyPairPo = keyDao.getKey(cacheKey);

        if (!unDecorateString(info.getPublicKey(), RsaUtil.PUBLIC_KEY).equals(keyPairPo.getPublicKey())) {
            logger.info("public key is't same");
            return null;
        }

        byte[] base64key = Base64Utils.decodeFromString(keyPairPo.getPrivateKey());
        PrivateKey privateKey = RsaUtil.restorePrivateKey(base64key);
        //私钥解密
        String decodedPw =
                RsaUtil.RSADecode(privateKey, Base64Utils.decodeFromString(info.getPassword()));
        String decodedName =
                RsaUtil.RSADecode(privateKey, Base64Utils.decodeFromString(info.getUserName()));

        return new UserInfo(decodedPw, decodedName,info.getFrom());
    }

    /**
     * 增加前文
     *
     * @param str
     * @param privateOrPublic
     * @return
     */
    private String decorateString(String str, String privateOrPublic) {
        if (privateOrPublic.equals(RsaUtil.PRIVATE_KEY)) {
            str = "-----BEGIN RSA PRIVATE KEY-----\n" + str + "\n-----END RSA PRIVATE KEY-----";
        }

        if (privateOrPublic.equals(RsaUtil.PUBLIC_KEY)) {
            str = "-----BEGIN RSA PUBLIC KEY-----\n" + str + "\n-----END RSA PUBLIC KEY-----";
        }
        return str;
    }

    /**
     * 去除修饰
     *
     * @param str
     * @param privateOrPublic
     * @return
     */
    private String unDecorateString(String str, String privateOrPublic) {
        if (privateOrPublic.equals(RsaUtil.PRIVATE_KEY)) {
            str =
                    str.trim().replace("-----BEGIN RSA PRIVATE KEY-----\n", "").replace("\n-----END RSA PRIVATE KEY-----", "");
        }
        if (privateOrPublic.equals(RsaUtil.PUBLIC_KEY)) {
            str =
                    str.trim().replace("-----BEGIN RSA PUBLIC KEY-----\n", "").replace("\n-----END RSA PUBLIC KEY-----", "");
        }
        return str;
    }
}
