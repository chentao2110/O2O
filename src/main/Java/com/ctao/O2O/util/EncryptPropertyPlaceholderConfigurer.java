package com.ctao.O2O.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    //需要解密字段的数组
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    /**
     * 对关键信息进行转换
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)){
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }else {
            return propertyValue;
        }

    }

    /**
     * 判断是否解密
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName){
        for (String encryptPropName : encryptPropNames){
            if (encryptPropName.equals(propertyName)){
                return true;
            }
        }
        return false;
    }
}
