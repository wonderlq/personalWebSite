package com.website.learn.bean.po;

/**
 * 公钥私钥对
 *
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-18 12:23
 */
public class KeyPairPo {

    private String publicKey;

    private String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public KeyPairPo(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public KeyPairPo() {
    }
}
