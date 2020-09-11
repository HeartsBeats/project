package com.example.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ProjectName: project-demo
 * @Package: com.example.common
 * @ClassName: AppFileUtil
 * @Author: 游佳琪
 * @Description: 文件上传下载工具类
 * @Date: 2020-9-7 09:20
 * @Version: 1.0
 */
public class AppFileUtil {
    //    文件上传保存路径
    public static String UPLOAD_PATH = "C:/Maven/StudyTools/upload";

    static {
//        	读取配置文件的存储地址
        InputStream filepath = AppFileUtil.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property = properties.getProperty("filepath");
        if (property != null) {
            UPLOAD_PATH = property;
        }
    }

    /**
     * 根据文件老名字得到新名字
     *
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {
        String stuff = oldName.substring(oldName.lastIndexOf("."),oldName.length());
//        获取文件唯一的uu值和新文件名组合
        return IdUtil.simpleUUID().toUpperCase() + stuff;
    }

    /**
     * 文件下载
     *
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1,构造文件对象
        File file = new File(UPLOAD_PATH, path);
        if (file.exists()) {
//            将下载的文件封装在字节数组内
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
            //创建封装响应头信息的对象
            HttpHeaders header = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置下载的文件的名称
//			header.setContentDispositionFormData("attachment", "123.jpg");
            //创建ResponseEntity对象
            ResponseEntity<Object> entity =
                    new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }

    /**
     * 根据路径改名字 去掉_temp
     *
     * @param goodsimg
     * @return
     */
    public static String renameFile(String goodsimg) {
//        获取文件对象
        File file = new File(UPLOAD_PATH, goodsimg);
//        生成新文件名
        String replace = goodsimg.replace("_temp", "");
        if (file.exists()) {
//            使用文件对象更改文件名
            file.renameTo(new File(UPLOAD_PATH, replace));
        }
//        返回新文件名
        return replace;
    }

    /**
     * 根据老路径删除图片
     *
     * @param oldPath
     */
    public static void removeFileByPath(String oldPath) {
//        判断是否为默认图片
        if (!oldPath.equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG)) {
//            根据老路径创建文件对象
            File file = new File(UPLOAD_PATH, oldPath);
            if (file.exists()) {
//                使用文件对象进行删除操作
                file.delete();
            }
        }
    }

}
