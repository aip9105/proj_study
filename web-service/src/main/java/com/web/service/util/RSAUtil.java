package com.web.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class RSAUtil {

    /**
     * 方法说明 : RSA加密字符串
     *
     * @param paramKey    密钥对象
     * @param paramString 待加密的字符串
     * @return Hex编码的加密后的数组
     * @date 2017/8/4
     */
    public static String encrypt(Key paramKey, String paramString) throws Exception {
        return rsaEncrypt(paramKey, paramString);
    }

    /**
     * 方法说明 : RSA解密
     *
     * @param paramKey    密钥对象
     * @param paramString 待解密的字符串（Hex编码过的数组）
     * @date 2017/8/4
     */
    public static String decrypt(Key paramKey, String paramString) throws Exception {
        return rsaDecrypt(paramKey, paramString);
    }

    /**
     * 方法说明 : RSA解密
     *
     * @param paramKey         密钥对象
     * @param paramArrayOfByte 待解密的数组
     * @return 解密后的数组
     * @date 2017/8/4
     */
    public static byte[] decrypt(Key paramKey, byte[] paramArrayOfByte) throws Exception {
        return rsaDecrypt(paramKey, paramArrayOfByte);
    }

    /**
     * 方法说明 : 创建RSA密钥对
     *
     * @return RSA密钥对
     * @date 2017/8/4
     */
    public static KeyPair rsaCreateKeyPair() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance("rsa").generateKeyPair();
    }

    /**
     * 方法说明 : RSA解密
     *
     * @param paramKey    密钥对象
     * @param paramString 待解密的字符串（Hex编码过的数组）
     * @return 解密后的数组（用默认字符集解码构造的字符串）
     * @date 2017/8/4
     */
    public static String rsaDecrypt(Key paramKey, String paramString)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, DecoderException {
        return new String(rsaDecrypt(paramKey, Hex.decodeHex(paramString.toCharArray())));
    }

    /**
     * 方法说明 : RSA解密
     *
     * @param paramKey         密钥对象
     * @param paramArrayOfByte 待解密的数组
     * @return 解密后的数组
     * @date 2017/8/4
     */
    public static byte[] rsaDecrypt(Key paramKey, byte[] paramArrayOfByte)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher localCipher = Cipher.getInstance("rsa");
        localCipher.init(Cipher.DECRYPT_MODE, paramKey);
        return localCipher.doFinal(paramArrayOfByte);
    }

    /**
     * 方法说明 : RSA加密字符串
     *
     * @param paramKey    密钥对象
     * @param paramString 待加密的字符串
     * @return Hex编码的加密后的数组
     * @date 2017/8/4
     */
    public static String rsaEncrypt(Key paramKey, String paramString) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher localCipher = Cipher.getInstance("rsa");
        localCipher.init(Cipher.ENCRYPT_MODE, paramKey);
        byte[] arrayOfByte = localCipher.doFinal(paramString.getBytes());
        return new String(Hex.encodeHex(arrayOfByte));
    }

    /**
     * 方法说明 : 从数组加载RSA私钥
     *
     * @param paramArrayOfByte 要加载的数组
     * @return RSA私钥（RSAPrivateKey的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadBinaryPrivateKey(byte[] paramArrayOfByte) throws InvalidKeyException {
        return RSAPrivateCrtKeyImpl.newKey(paramArrayOfByte);
    }

    /**
     * 方法说明 : 从文件加载RSA私钥
     *
     * @param paramFile 文件对象
     * @return RSA私钥（RSAPrivateKey的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadBinaryPrivateKey(File paramFile) throws IOException, InvalidKeyException {
        byte[] arrayOfByte = loadBinaryFile(paramFile);
        return rsaLoadBinaryPrivateKey(arrayOfByte);
    }

    /**
     * 方法说明 : 从数组加载RSA公钥
     *
     * @param paramArrayOfByte 要加载的数组
     * @return RSA公钥（RSAPublicKeyImpl的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadBinaryPublicKey(byte[] paramArrayOfByte) throws InvalidKeyException {
        return new RSAPublicKeyImpl(paramArrayOfByte);
    }

    /**
     * 方法说明 : 从文件加载RSA公钥
     *
     * @param paramFile 文件对象
     * @return RSA公钥（RSAPublicKeyImpl的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadBinaryPublicKey(File paramFile) throws IOException, InvalidKeyException {
        byte[] arrayOfByte = loadBinaryFile(paramFile);
        return rsaLoadBinaryPublicKey(arrayOfByte);
    }

    /**
     * 方法说明 : 从文件加载RSA私钥（内容被Hex编码过）
     *
     * @param paramFile 文件对象
     * @return RSA私钥（RSAPrivateKey的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadHexEncodedPrivateKey(File paramFile) throws IOException, DecoderException, InvalidKeyException {
        byte[] arrayOfByte = lodaHexEncodedFile(paramFile);
        return RSAPrivateCrtKeyImpl.newKey(arrayOfByte);
    }

    /**
     * 方法说明 : 从Hex编码的字符串加载私钥
     *
     * @param paramString 经过编码的字符串
     * @return RSA私钥（RSAPrivateKey的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadHexEncodedPrivateKey(String paramString) throws InvalidKeyException, DecoderException {
        return RSAPrivateCrtKeyImpl.newKey(Hex.decodeHex(paramString.toCharArray()));
    }

    /**
     * 方法说明 : 从文件加载RSA公钥（内容被Hex编码过）
     *
     * @param paramFile 文件对象
     * @return RSA公钥（RSAPublicKeyImpl的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadHexEncodedPublicKey(File paramFile) throws IOException, DecoderException, InvalidKeyException {
        byte[] arrayOfByte = lodaHexEncodedFile(paramFile);
        return new RSAPublicKeyImpl(arrayOfByte);
    }

    /**
     * 方法说明 : 从Hex编码的字符串加载公钥
     *
     * @param paramString 经过编码的字符串
     * @return RSA公钥（RSAPublicKeyImpl的实例）
     * @date 2017/8/4
     */
    public static Key rsaLoadHexEncodedPublicKey(String paramString) throws InvalidKeyException, DecoderException {
        return new RSAPublicKeyImpl(Hex.decodeHex(paramString.toCharArray()));
    }

    /**
     * 方法说明 : 保存密钥为文件
     *
     * @param paramFile 目标文件
     * @param paramKey  密钥对象
     * @date 2017/8/4
     */
    public static void rsaStoreBinaryKey(File paramFile, Key paramKey) throws IOException {
        storeBinaryFile(paramFile, paramKey.getEncoded());
    }

    /**
     * 方法说明 : 返回基本编码格式的密钥
     *
     * @param paramKey 密钥对象
     * @return 基本编码格式对密钥
     * @date 2017/8/4
     */
    public static byte[] rsaStoreBinaryKey(Key paramKey) {
        return paramKey.getEncoded();
    }

    /**
     * 方法说明 : 保存密钥为文件（文件内容用Hex编码过）
     *
     * @param paramFile 目标文件
     * @param paramKey  密钥对象
     * @date 2017/8/4
     */
    public static void rsaStoreHexEncodedKey(File paramFile, Key paramKey) throws IOException {
        storeHexEncodedFile(paramFile, paramKey.getEncoded());
    }

    /**
     * 方法说明 : 返回用Hex编码过的基本编码格式的密钥
     *
     * @param paramKey 密钥对象
     * @return 用Hex编码过的基本编码格式的密钥
     * @date 2017/8/4
     */
    public static String rsaStoreHexEncodedKey(Key paramKey) {
        return new String(Hex.encodeHex(paramKey.getEncoded()));
    }

    /**
     * 方法说明 : 读取JavaKeyStore数据库
     *
     * @param paramFile   KeyStore数据库文件对象
     * @param paramString 密码
     * @return JavaKeyStore对象
     * @date 2017/8/4
     */
    public static KeyStore rsaReadKeyStoreFile(File paramFile, String paramString) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore localKeyStore = KeyStore.getInstance("JKS");
        localKeyStore.load(new FileInputStream(paramFile), paramString != null ? paramString.toCharArray() : null);
        return localKeyStore;
    }

    /**
     * 方法说明 : 加载二进制文件
     *
     * @param paramFile 文件对象
     * @return 读取到的数组
     * @date 2017/8/4
     */
    private static byte[] loadBinaryFile(File paramFile) throws IOException {
        byte[] arrayOfByte;
        try (FileInputStream localFileInputStream = new FileInputStream(paramFile)) {
            arrayOfByte = new byte[localFileInputStream.available()];
            int num = localFileInputStream.read(arrayOfByte);
            log.info("长度", num);
        }
        return arrayOfByte;
    }

    /**
     * 方法说明 : 加载二进制文件（内容被Hex编码过）
     *
     * @param paramFile 文件对象
     * @return 解码后的数组
     * @date 2017/8/4
     */
    private static byte[] lodaHexEncodedFile(File paramFile) throws IOException, DecoderException {
        byte[] arrayOfByte = loadBinaryFile(paramFile);
        return Hex.decodeHex(new String(arrayOfByte).toCharArray());
    }

    /**
     * 方法说明 : 二进制存入文件
     *
     * @param paramFile        目标文件
     * @param paramArrayOfByte 要写入文件的数组
     * @date 2017/8/4
     */
    private static void storeBinaryFile(File paramFile, byte[] paramArrayOfByte) throws IOException {
        try (FileOutputStream localFileOutputStream = new FileOutputStream(paramFile)) {
            localFileOutputStream.write(paramArrayOfByte);
        }
    }

    /**
     * 方法说明 : 二进制存入文件（先用Hex编码内容再写入文件）
     *
     * @param paramFile        目标文件
     * @param paramArrayOfByte 要写入文件的数组
     * @date 2017/8/4
     */
    private static void storeHexEncodedFile(File paramFile, byte[] paramArrayOfByte) throws IOException {
        storeBinaryFile(paramFile, new String(Hex.encodeHex(paramArrayOfByte)).getBytes());
    }
}
