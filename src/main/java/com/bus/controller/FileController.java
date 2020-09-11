package com.bus.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.AppFileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: FileController
 * @Author: 游佳琪
 * @Description: 文件上传控制器
 * @Date: 2020-9-6 22:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("uploadFile")
    public Map<String, Object> uploadFile(MultipartFile mf) {
//       1.获取文件名
        String fileName = mf.getOriginalFilename();
//       2,根据文件名生成新的文件名
        String newFileName = AppFileUtil.createNewFileName(fileName);
        //3,得到当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
//        4,构造文件夹
        File dirfile = new File(AppFileUtil.UPLOAD_PATH, dirName);
//        6.判断文件是否存在
        if (!dirfile.exists()) {
//            创建文件夹
            dirfile.mkdirs();
        }
//        7.创建文件对象
        File file = new File(dirfile,newFileName+"_temp");
//        8.将multipartFile中的图片信息写入文件中
        try {
            mf.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("path",dirName+"/"+newFileName+"_temp");
        return map;
    }
    /**
     * 图片下载
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path){
        return AppFileUtil.createResponseEntity(path);
    }

}
