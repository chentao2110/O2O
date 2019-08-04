package com.ctao.O2O.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DESUtil {
    private static Key key;
    //设置秘钥key
    private static String KEY_STR = "myKey";
    private static String CHERSETNAME = "UTF-8";
    private static  String ALGORITHM ="DES";
    static {
        try {
            //生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置秘钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1算法对象
            generator.init(secureRandom);
            //生成秘钥对象
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取加密后的信息
     * @param str
     * @return
     */
    public static  String getEncryptString(String str){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try{
            //按UTF-8编码
            byte[] bytes = str.getBytes(CHERSETNAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化加密对象
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //返回加密好的信息
            return  base64Encoder.encode(doFinal);
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }

    public static  String getDecryptString(String str){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] doFinal = cipher.doFinal(bytes);
            return  new String(doFinal,CHERSETNAME);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(getEncryptString("Chentao164283;"));
        System.out.println(getEncryptString("work"));
    }
}
