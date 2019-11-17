package org.openmore.common.polyv;//类AESCryptoUtils
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
 
/**
 * AES对称加密的工具类，支持Hex与Base64两种编码方式。
 */
public class AESCryptoUtils {
 
    private static final String AES = "AES";
    private static final int DEFAULT_AES_KEYSIZE = 128;
    public static final byte[] DEFAULT_IV = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
 
    /**
     * 使用AES算法<code>AES/CBC/PKCS5Padding</code>模式加密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] input, byte[] key) {
        return encrypt(input, key, DEFAULT_IV);
    }
 
    /**
     * 使用AES算法<code>AES/CBC/PKCS5Padding</code>模式加密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @param iv 符合AES要求的初始向量
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv) {
        return encrypt(input, key, iv, "AES/CBC/PKCS5Padding");
    }
 
    /**
     * 使用AES算法加密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @param iv 符合AES要求的初始向量
     * @param transformation 变换模式，例如“AES/CBC/PKCS5Padding”， “AES/CBC/PKCS5Padding”
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv, String transformation) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivParam = new IvParameterSpec(iv);
 
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParam);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw convertRuntimeException(e);
        }
    }
 
    /**
     * 使用AES算法<code>AES/CBC/PKCS5Padding</code>模式解密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] input, byte[] key) {
        return decrypt(input, key, DEFAULT_IV);
    }
 
    /**
     * 使用AES算法<code>AES/CBC/PKCS5Padding</code>模式解密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @param iv 符合AES要求的初始向量
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv) {
        return decrypt(input, key, iv, "AES/CBC/PKCS5Padding");
    }
 
    /**
     * 使用AES算法解密无编码的原始字节数组，返回无编码的字节数组结果。
     * @param input 原始字节数组
     * @param key 符合AES要求的秘钥
     * @param iv 符合AES要求的初始向量
     * @param transformation 变换模式，例如“AES/CBC/PKCS5Padding”， “AES/CBC/PKCS5Padding”
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv, String transformation) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivParam = new IvParameterSpec(iv);
 
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw convertRuntimeException(e);
        }
    }
 
    private static IllegalStateException convertRuntimeException(Exception e) {
        return new IllegalStateException("Security exception", e);
    }
 
    /**
     * 生成AES秘钥，返回字节数组，长度为128位（16字节）。
     */
    public static byte[] generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(DEFAULT_AES_KEYSIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw convertRuntimeException(e);
        }
    }
 
    /**
     * 生成AES秘钥，返回字节数组，长度为128位（16字节）。
     * @param password 密码
     */
    public static byte[] generateKey(String password) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(DEFAULT_AES_KEYSIZE, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw convertRuntimeException(e);
        }
    }
 
    /**
     * 使用AES加密原始字符串，返回Hex编码结果。
     * @param input 原始输入字符串
     * @param keyBytes 符合AES要求的秘钥
     * @return 加密后Hex编码的字符串
     */
    public static String encryptToHex(String input, byte[] keyBytes) {
        byte[] encryptResult = encrypt(input.getBytes(), keyBytes);
        return Hex.encodeHexString(encryptResult);
    }
 
    /**
     * 使用AES加密原始字符串，返回Base64编码结果。
     * @param input 原始输入字符串
     * @param keyBytes 符合AES要求的秘钥
     * @return 加密后Base64编码的字符串
     */
    public static String encryptToBase64(String input, byte[] keyBytes) {
        byte[] encryptResult = encrypt(input.getBytes(), keyBytes);
        // return Base64.encodeBase64String(encryptResult);
        return new String(Base64.encodeBase64(encryptResult));
    }
 
    /**
     * 使用AES解密Hex编码的加密字符串，返回原始字符串。
     * @param input Hex编码的加密字符串
     * @param keyBytes 符合AES要求的秘钥
     * @return 加密字符串的明文
     */
    public static String decryptFromHex(String input, byte[] keyBytes) {
        try {
            byte[] decryptResult = decrypt(Hex.decodeHex(input.toCharArray()), keyBytes);
            return new String(decryptResult);
        } catch (Exception e) {
            throw convertRuntimeException(e);
        }
    }
 
    /**
     * 使用AES解密Base64编码的加密字符串，返回原始字符串。
     * @param input Base64编码的加密字符串
     * @param keyBytes 符合AES要求的秘钥
     * @return 加密字符串的明文
     */
    public static String decryptFromBase64(String input, byte[] keyBytes) {
        byte[] decryptResult = decrypt(Base64.decodeBase64(input.getBytes()), keyBytes);
        return new String(decryptResult);
    }
}