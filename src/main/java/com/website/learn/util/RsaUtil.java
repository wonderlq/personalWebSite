package com.website.learn.util;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-16 00:06
 */
public class RsaUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "public_key";
    public static final String PRIVATE_KEY = "private_key";

    public static final int KEY_SIZE = 1024;

    public static final String PLAIN_TXT = "My Conquest is the Sea of Stars";

    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashedMap();
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());

            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec pkcs8EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(pkcs8EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String RSADecode(PrivateKey key, byte[] encodeTxt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodeTxt), Charset.forName("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, byte[]> keyMap = generateKeyBytes();

        System.out.println(Base64Utils.encodeToString(keyMap.get(PUBLIC_KEY)));
        //加密
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));

        byte[] encodedText = RSAEncode(publicKey, PLAIN_TXT.getBytes());

        System.out.println("===============");
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        String decodedText = RSADecode(privateKey, encodedText);
        System.out.println(decodedText);


        String ss = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGwaJH1WNheGj7RxfrLyFS3LOu\\nusvopdYvYOF3sYPNoyWWInCGpGentwJMZuilRfrkabGmwwTQi2M4CO20aHXc+J7O\\npm0URm4iDtH+M7Rd64Q+3J1HjhzQ6NOSE\\/C6FQRmhcupQceL7vTfnf9CigJqw+f4\\n0kLq\\/cJUvgyiy8Ly3wIDAQAB";
        System.out.println(ss.getBytes());
        System.out.println("gg");
    }
}
