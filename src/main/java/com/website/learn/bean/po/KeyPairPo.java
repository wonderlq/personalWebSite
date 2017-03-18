package com.website.learn.bean.po;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 公钥私钥对
 *
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-18 12:23
 */
public class KeyPairPo {

    private final String publicKey;

    private final String privateKey;

    public KeyPairPo(Builder builder) {
        this.privateKey = builder.innerPrivateKey;
        this.publicKey = builder.innerPublicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public static class Builder {
        private String innerPublicKey;
        private String innerPrivateKey;

        public Builder setPublicKey(String publicKey) {
            this.innerPublicKey = publicKey;
            return this;
        }

        public Builder setPrivateKey(String privateKey) {
            this.innerPrivateKey = privateKey;
            return this;
        }

        public KeyPairPo build() {
            return new KeyPairPo(this);
        }
    }
}
