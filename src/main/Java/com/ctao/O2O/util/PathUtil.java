package com.ctao.O2O.util;

public class PathUtil {

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");//获取操作系统的名字
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath="D:/projectdev/image";
        } else {
            basePath = "/home/chentao/image";
        }
        basePath = basePath.replace("\\" ,"/");
        return basePath;
    }
    public static String getShopImgPath(long shopId){
        String imgPath = "/upload/item/shop/"+shopId+"/";
        return imgPath.replace("\\" ,"/");
    }
}
