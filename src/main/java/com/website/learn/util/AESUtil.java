package com.website.learn.util;

import com.google.common.base.Charsets;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

 // 加密
    public static String encrypt(String content, String password) {
        try {
            byte[] raw = password.getBytes(Charsets.UTF_8);
            if (raw.length != 16) {
                throw new IllegalArgumentException("Invalid key size." + password);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16])); // zero IV
            byte[] encry = cipher.doFinal(content.getBytes(Charsets.UTF_8));
            return Base64.encodeBase64String(encry);
        } catch (Exception e) {
            logger.error("encrypt failed,content:{}",content,e);
        }
        return null;
    }

    public static String decrypt(String content, String password) {
        try {
            byte[] contentByte = Base64.decodeBase64(content);
            byte[] raw = password.getBytes(Charsets.UTF_8);
            if (raw.length != 16) {
                throw new IllegalArgumentException("Invalid key size. " + password);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(contentByte);

            return new String(original, Charsets.UTF_8);
        } catch (Exception e) {
            logger.error("decrypt failed,content:{}",content,e);
        }
        return null;
    }
    
    public static void main(String[] args){
        String content = "abcdefghigklmnopqrstuvwxyz0123456789";
        String pw = "1234567890123456";
        System.out.println("原文："+content);
        String encryCon = encrypt(content,pw);
        System.out.println("密文："+encryCon);
        System.out.println("还原文："+decrypt(encryCon,pw));
    }
}
