package com.ctao.O2O.util;

import com.ctao.O2O.dto.ImageHoder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormate= new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile转化为File
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }
    /**
     * 图片添加水印，并返回图片的URL
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbail(ImageHoder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String  extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr+realFileName+extension;
        logger.debug("current ralativeAddr is "+relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete addr is "+PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("basePath is :" + basePath);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败"+e.toString());
        }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * storePath是文件的路径或者是目录的路径，如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for (int i=0 ; i<files.length ; i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
    /**
     * 获取输入文件流的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));

    }

    /**
     * 生成随机文件名，当前年月日时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr = sDateFormate.format(new Date());
        return nowTimeStr+rannum;
    }

    public static String generateNormalImg(ImageHoder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String  extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr+realFileName+extension;
        logger.debug("current ralativeAddr is "+relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete addr is "+PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("basePath is :" + basePath);
        try {
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败"+e.toString());
        }
        return relativeAddr;
    }
}
