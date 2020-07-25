package com.web.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密算法
 *
 * @author sunwill
 */
public final class AESUtil {

    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private AESUtil() {
        //do something
    }

    /**
     * AES加密算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     */
    private static final String IVPARAMETER = "SUNWILLFINDHYAES";

    /**
     * 此处使用AES-128-CBC加密模式，key需要为16位
     */
    private static final String KEY = "FESCOSINOSOFTOUT";

    /**
     * 加密-解密算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 方法说明: 加密
     * @param content 参数
     */
    public static String encrypt(String content) {
        try {
            if (null != content) {
                byte[] raw = KEY.getBytes();
                SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));

                return byteToHex(encrypted).toLowerCase();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * 方法说明:解密
     * @param content 参数
     */
    public static String decrypt(String content) {
        try {
            if (null != content) {
                byte[] raw = KEY.getBytes("ASCII");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] encrypted1 = hexToByte(content);
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, "utf-8");
            }
            return null;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 方法说明: 十六进制转为字节
     * @param strhex 字符串
     */
    private static byte[] hexToByte(String strhex) {
        if (strhex == null) {
            return new byte[0];
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return new byte[0];
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    /**
     * 方法说明: 转为十六进制
     * @param b 参数
     */
    private static String byteToHex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }
}
