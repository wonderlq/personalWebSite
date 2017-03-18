package com.website.learn.bean.bo;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-18 12:59
 */
public class KeyData {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * redis的key
     */
    private String fileKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public KeyData(String publicKey, String fileKey) {
        this.publicKey = publicKey;
        this.fileKey = fileKey;
    }
}
