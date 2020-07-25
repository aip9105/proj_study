package com.web.service.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class KeyPairTool {

    /**
     * 方法说明 : 从KeyStore中获取证书
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param password     KeyStore保护密码

     * @date 2017/8/4
     */
    public Certificate getCertificateByAlias(String keyStorePath, String alias, String password) {
        Certificate cert = null;
        try {
            if (!isKeyStoreLoaded) {
                // 加载KeyStore数据文件
                this.loadKeyStore(keyStorePath, password);
            }
            if (keyStore != null) {
                // 通过别名获取证书
                cert = keyStore.getCertificate(alias);
            }
        } catch (KeyStoreException e) {
            log.error("KeyPairTool转换出错:",e);
        }
        return cert;
    }

    /**
     * 方法说明 : 从KeyStore中获取密钥对
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param privatePass  私钥密码
     * @param keyStorePass KeyStore保护密码

     * @date 2017/8/4
     */
    public KeyPair getKeyPairByAlias(String keyStorePath, String alias, String privatePass, String keyStorePass) {
        KeyPair keyPair = null;
        PublicKey publicKey = this.getPublicKeyByAlias(keyStorePath, alias, keyStorePass);
        if (publicKey != null) {
            PrivateKey privateKey = this.getPrivateKeyByAlias(keyStorePath, alias, privatePass, keyStorePass);
            if (privateKey != null) {
                keyPair = new KeyPair(publicKey, privateKey);
            }
        }
        return keyPair;
    }

    /**
     * 方法说明 : 获取私钥
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param privatePass  私钥密码
     * @param keyStorePass KeyStore保护密码

     * @date 2017/8/4
     */
    public PrivateKey getPrivateKeyByAlias(String keyStorePath, String alias, String privatePass, String keyStorePass) {
        PrivateKey privateKey = null;
        try {
            if (!isKeyStoreLoaded) {
                // 加载KeyStore数据文件
                this.loadKeyStore(keyStorePath, keyStorePass);
            }
            Key key = keyStore.getKey(alias, privatePass.toCharArray());
            if (key instanceof PrivateKey) {
                privateKey = (PrivateKey) key;
            }
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            log.error("KeyPairTool转换出错:",e);
        }
        return privateKey;
    }

    /**
     * 方法说明 : 获取公钥
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param password     KeyStore保护密码

     * @date 2017/8/4
     */
    public PublicKey getPublicKeyByAlias(String keyStorePath, String alias, String password) {
        // 获取证书
        Certificate cert = this.getCertificateByAlias(keyStorePath, alias, password);
        if (cert != null) {
            return cert.getPublicKey();
        }
        return null;
    }

    /**
     * 方法说明 : 把证书的编码形式保存为文件
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param password     KeyStore保护密码

     * @date 2017/8/4
     */
    public void exportCert(String keyStorePath, String alias, String password) {
        Certificate cert = this.getCertificateByAlias(keyStorePath, alias, password);
        if (cert != null) {
            try {
                this.exportData(cert.getEncoded(), System.getProperty("user.home") + "/" + "certificate.bytes");
            } catch (CertificateEncodingException e) {
                log.error("KeyPairTool转换出错:",e);
            }
        }
    }

    /**
     * 方法说明 : 把私钥保存为文件
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param privatePass  私钥密码
     * @param keyStorePass KeyStore保护密码
     * @param isBase64     是否使用BASE64编码

     * @date 2017/8/4
     */
    public void exportPrivateKeyBytes(String keyStorePath, String alias, String privatePass, String keyStorePass, boolean isBase64) {
        PrivateKey privateKey = this.getPrivateKeyByAlias(keyStorePath, alias, privatePass, keyStorePass);
        if (privateKey != null) {
            byte[] data = privateKey.getEncoded();
            if (isBase64) {
                String afBase64 = Base64.getEncoder().encodeToString(data);
                afBase64 = "—–BEGIN PRIVATE KEY—–/n" + afBase64 + "/n—–END PRIVATE KEY—–";
                data = afBase64.getBytes();
            }
            this.exportData(data, System.getProperty("user.home") + "/" + "privateKey.bytes");
        }
    }

    /**
     * 方法说明 : 把公钥保存为文件
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param alias        密钥对别名
     * @param password     KeyStore保护密码
     * @param isBase64     是否使用BASE64编码

     * @date 2017/8/4
     */
    public void exportPublicKeyBytes(String keyStorePath, String alias, String password, boolean isBase64) {
        PublicKey publicKey = this.getPublicKeyByAlias(keyStorePath, alias, password);
        if (publicKey != null) {
            byte[] data = publicKey.getEncoded();
            if (isBase64) {
                String afBase64 = Base64.getEncoder().encodeToString(data);
                afBase64 = "—–BEGIN PUBLIC KEY—–/n" + afBase64 + "/n—–END PUBLIC KEY—–";
                data = afBase64.getBytes();
            }
            this.exportData(data, System.getProperty("user.home") + "/" + "publicKey.bytes");
        }
    }

    /**
     * 方法说明 : 从文件读取证书
     *
     * @param fileName 要载入到内存的证书路径

     * @date 2017/8/4
     */
    public Certificate loadCertificate(String fileName) {
        Certificate cert = null;
        FileInputStream fileInputStream = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            File file = new File(fileName);
            if (file.exists() && file.canRead() && file.isFile()) {
                fileInputStream = new FileInputStream(file);
                cert = certFactory.generateCertificate(fileInputStream);
            }
        } catch (CertificateException | FileNotFoundException e) {
            log.error("KeyPairTool转换出错:",e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("KeyPairTool转换出错:",e);
                }
            }
        }
        return cert;
    }

    /**
     * 方法说明 : 从文件读取公钥
     *
     * @param fileName 要载入到内存的公钥路径

     * @date 2017/8/4
     */
    public PublicKey loadPublicKey(String fileName) {
        PublicKey publicKey = null;
        try {
            byte[] data = this.loadData(fileName);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("KeyPairTool转换出错:",e);
        }
        return publicKey;
    }

    /**
     * 方法说明 : 从文件读取私钥
     *
     * @param fileName 要载入到内存的公钥路径

     * @date 2017/8/4
     */
    public PrivateKey loadPrivateKey(String fileName) {
        PrivateKey privateKey = null;
        try {
            byte[] data = this.loadData(fileName);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("KeyPairTool转换出错:",e);
        }
        return privateKey;
    }

    /**
     * 方法说明 : 从指定路径加载KeyStore数据文件
     *
     * @param keyStorePath KeyStore数据文件路径
     * @param password     KeyStore保护密码

     * @date 2017/8/4
     */
    private void loadKeyStore(String keyStorePath, String password) {
        try {
            if (!isKeyStoreLoaded) {
                File keyStoreFile = new File(keyStorePath);
                if (keyStoreFile.exists() && keyStoreFile.canRead() && keyStoreFile.isFile()) {
                    // 初始化指导类型的KeyStore并加载数据文件
                    keyStore = KeyStore.getInstance("RSA");
                    try (FileInputStream fileInputStream = new FileInputStream(keyStoreFile)) {
                        keyStore.load(fileInputStream, password.toCharArray());
                    }
                    isKeyStoreLoaded = true;
                }
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            log.error("KeyPairTool转换出错:",e);
        }
    }

    /**
     * 方法说明 : 输出文件
     *
     * @param data     写入文件的数组
     * @param fileName 要保存的文件路径及名称

     * @date 2017/8/4
     */
    private void exportData(byte[] data, String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(fileName);
            if (file.canWrite()) {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data);
                fileOutputStream.close();
            }
        } catch (IOException e) {
            log.error("KeyPairTool转换出错:",e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                log.error("KeyPairTool转换出错:",e);
            }
        }
    }

    /**
     * 方法说明 : 将指定文件读入内存，byte[]形式
     *
     * @param fileName 要载入到内存的文件路径

     * @date 2017/8/4
     */
    private byte[] loadData(String fileName) {
        byte[] data = null;
        RandomAccessFile raf = null;
        FileChannel fileChannel = null;
        try {
            File file = new File(fileName);
            if (file.exists() && file.canRead() && file.isFile()) {
                raf = new RandomAccessFile(file, "r");
                fileChannel = raf.getChannel();
                MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).load();
                data = new byte[(int) fileChannel.size()];
                if (byteBuffer.remaining() > 0) {
                    byteBuffer.get(data, 0, byteBuffer.remaining());
                }
            }
        } catch (IOException e) {
            log.error("KeyPairTool转换出错:",e);
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    log.error("KeyPairTool转换出错:",e);
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    log.error("KeyPairTool转换出错:",e);
                }
            }
        }
        return data;
    }

    /**
     * 加载的KeyStore
     */
    private KeyStore keyStore;

    /**
     * KeyStore是否已加载，如果要重新加载，set一个false
     */
    private boolean isKeyStoreLoaded;

    /**
     * 方法说明 : 如果已加载，返回加载的KeyStore，否则返回null
     *

     * @date 2017/8/4
     */
    public KeyStore getKeyStore() {
        if (isKeyStoreLoaded) {
            return keyStore;
        } else {
            return null;
        }
    }

    /**
     * 方法说明 : 判断是否已加载KeyStore
     *

     * @date 2017/8/4
     */
    public boolean isKeyStoreLoaded() {
        return isKeyStoreLoaded;
    }

    /**
     * 方法说明 : 重装设置KeyStore加载状态为未加载
     *

     * @date 2017/8/4
     */
    public void resetKeyStoreLoaded() {
        this.isKeyStoreLoaded = false;
    }

}
