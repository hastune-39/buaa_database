package com.example.bighomework.controller;

import com.example.bighomework.mapper.BrowseMapper;
import com.example.bighomework.mapper.KeywordMapper;
import com.example.bighomework.mapper.PictureMapper;
import com.example.bighomework.pojo.Keyword;
import com.example.bighomework.pojo.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class PictureController {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private BrowseMapper browseMapper;


    @RequestMapping(value = "/pictures/selectAll", method = RequestMethod.GET)
    public List<Picture> selectAllPictures() {
        try {
            List<Picture> pictures = pictureMapper.getAllPictures();
            //random
            Collections.shuffle(pictures);
            return pictures;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/painters/{painter_id}/upload", method = RequestMethod.POST)
    public String uploadPicture(@PathVariable int painter_id,
                                @RequestBody Picture picture) {
        try {
            picture.setPainter_id(painter_id);
            pictureMapper.insertOnePicture(picture);
            System.out.println("success upload picture!");
            return "400";
        } catch (Exception e) {
            e.printStackTrace();
            return "insert fail";
        }
    }

    //begin search

    /***
     public List<Picture> selectAllPictures(){

     }***/

    @RequestMapping(value = "/artworks/{painter_id}", method = RequestMethod.GET)
    public List<Picture> viewOnePainterAllPictures(@PathVariable int painter_id) {
        List<Picture> pictures = pictureMapper.queryPicturesByPainter_id(painter_id);
        return pictures;
    }

    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    public Picture viewPictureByID(@RequestParam int picture_id) {
        Picture picture = pictureMapper.queryPictureByPicture_id(picture_id);
        return picture;
    }

    @RequestMapping(value = "/Painter/Pictures", method = RequestMethod.GET)
    public List<Picture> getOnePainterAllPictures(@RequestParam int painter_id) {
        try {
            return pictureMapper.queryPicturesByPainter_id(painter_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     public List<Picture> selectPictureByTitle(){

     }***/

    @RequestMapping(value = "/pictures/searchKeyword", method = RequestMethod.GET)
    public List<Picture> selectPictureByKeyWord(@RequestParam String keyword) {
        System.out.println("关键字是: " + keyword);
        try {
            List<Picture> pictureList = pictureMapper.queryPicturesByKeyword(keyword);
            return pictureList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //end

    /***
     * 画师管理图片部分
     */
    @RequestMapping(value = "picture/get/keywords", method = RequestMethod.GET)
    public List<String> getOnePictureKeywords(@RequestParam int picture_id) {
        try {
            List<Keyword> keywords = keywordMapper.getOnePictureKeywords(picture_id);
            ArrayList<String> back = new ArrayList<>();
            for (Keyword Item : keywords) {
                back.add(Item.getKeyword());
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "picture/update/title", method = RequestMethod.POST)
    public String updatePictureTitle(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int picture_id = request.getInt("picture_id");
            String title = request.getString("title");
            pictureMapper.updatePictureTitle(picture_id, title);
            return "update-title-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "update-title-false!";
    }

    @RequestMapping(value = "picture/update/keywords", method = RequestMethod.POST)
    public String updatePictureKeywords(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            JSONArray keywords = request.getJSONArray("keywords");
            int picture_id = request.getInt("picture_id");

            //1.先删除原有关键字
            keywordMapper.deleteOnePictureKeyWords(picture_id);

            //2.插入新关键字
            for (int i = 0; i < keywords.length(); i++) {
                keywordMapper.insertKeyWord(new Keyword(picture_id, keywords.getString(i)));
            }
            return "update-keywords-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "update-keywords-false!";
    }

    @RequestMapping(value = "picture/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deletePicture(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int painter_id = request.getInt("painter_id");
            int picture_id = request.getInt("picture_id");


            //安全性验证
            int true_picture_id = pictureMapper.selectPictureAuthorID(picture_id);
            if (painter_id != true_picture_id) {
                System.out.println("用户" + painter_id + "没有删除图片" + picture_id+ "的权限!");
                return new ResponseEntity<String>("没有删除图片的权限!", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            //用触发器进行优化！
            //1.删除关键字
//            keywordMapper.deleteOnePictureKeyWords(picture_id);
            //2.删除图片
            pictureMapper.deletePicture(picture_id);

            return new ResponseEntity<String>("delete-picture-success!", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("delete-picture-fail!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /***
     * 浏览部分
     */
    @RequestMapping(value = "Picture/Browse", method = RequestMethod.POST)
    public String addPictureBrowse(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int user_id = request.getInt("user_id");
            int picture_id = request.getInt("picture_id");

            browseMapper.addPictureBrowse(user_id, picture_id);
            return "add-picture-browse-success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "add-picture-browse-fail";
    }

}
