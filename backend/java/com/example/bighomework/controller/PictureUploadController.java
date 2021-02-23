package com.example.bighomework.controller;

import com.example.bighomework.mapper.KeywordMapper;
import com.example.bighomework.mapper.PictureMapper;
import com.example.bighomework.mapper.UserMapper;
import com.example.bighomework.pojo.Keyword;
import com.example.bighomework.pojo.Picture;
import com.example.bighomework.pojo.User;
import com.example.bighomework.utils.FileUtils;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class PictureUploadController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public PictureUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private KeywordMapper keywordMapper;

    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/picture/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("painter_id") int painter_id, @RequestParam("file") MultipartFile file, Map<String, Object> map) {
        try {
            String localPath = "D:/code_software/buaa_database/font-bighm/static/img/" + userMapper.selectByID(painter_id).getUserName();
            // 上传成功或者失败的提示
            String msg = "";
            //重名操作?
            if (FileUtils.upload(file, localPath, file.getOriginalFilename())) {
                // 上传成功，给出页面提示
                msg = "上传成功！";
                System.out.println("上传成功!");
            } else {
                msg = "上传失败！";
                System.out.println("上传失败!");
            }
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/picture/uploadForm", method = RequestMethod.POST)
    public String uploadForm(@RequestBody String params) {
        try {
            //需要画师id,文件名,标题,关键字列表
            JSONObject request = new JSONObject(params);
            int painter_id = request.getInt("painter_id");
            int picture_id = pictureMapper.getMaxID() + 1;
            String fileName = request.getString("fileName");
            String title = request.getString("title");
            JSONArray keywords = request.getJSONArray("keywords");
            String localPath = "static/img/" + userMapper.selectByID(painter_id).getUserName() + "/" + fileName;

            //上传图片
            Picture picture = new Picture(picture_id, painter_id, localPath, title);
            pictureMapper.insertOnePicture(picture);


            //上传关键字
            for (int i = 0; i < keywords.length(); i++) {
                keywordMapper.insertKeyWord(new Keyword(picture_id, keywords.getString(i)));
            }
            return "upload form success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "upload form fail!";
    }

//    /**
//     * 显示单张图片
//     * @return
//     */
//    @RequestMapping("show")
//    public ResponseEntity showPhotos(String fileName){
//
//        try {
//            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
