package com.ctao.O2O.util;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");//获取分隔符
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");//获取操作系统的名字
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath="D:/projectdev/image/";
        } else {
            basePath = "/home/xiangze/image/";
        }
        basePath = basePath.replace("/" ,seperator);
        return basePath;
    }
    public static String getShopImgPath(long shopId){
        String imgPath = "upload/item/shop/"+shopId+"/";
        return imgPath.replace("/", seperator);
    }
}
