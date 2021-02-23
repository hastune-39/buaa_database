package com.example.bighomework.controller;

import com.example.bighomework.mapper.CollectionMapper;
import com.example.bighomework.pojo.Like;
import com.example.bighomework.pojo.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollectionController {

    @Autowired
    private CollectionMapper collectionMapper;

    @RequestMapping(value = "/collections/get", method = RequestMethod.GET)
    public List<Like> getCollections(@RequestParam int user_id) {
        try {
            return collectionMapper.selectOneUserAllCollections(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/collections/add", method = RequestMethod.POST)
    public String collect(@RequestBody String params) {
        try {
            JSONObject collection = new JSONObject(params);
            System.out.println("user_id为" + collection.getInt("user_id"));
            System.out.println("picture_id为" + collection.getInt("picture_id"));
            collectionMapper.addCollection(collection.getInt("user_id"),
                    collection.getInt("picture_id"));
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false!";
    }

    //post/delete等有什么区别？
    @RequestMapping(value = "/collections/cancel", method = RequestMethod.POST)
    public String CancelCollect(@RequestBody String params) {
        try {
            JSONObject collection = new JSONObject(params);
            collectionMapper.cancelCollection(collection.getInt("user_id"),
                    collection.getInt("picture_id"));
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false!";
    }

    @RequestMapping(value = "/collection/getPictures", method = RequestMethod.GET)
    public List<Picture> getCollectionPictures(@RequestParam int user_id) {
        try {
            System.out.println("正在搜集用户"+ user_id+"所有收藏的图片...");
            return collectionMapper.selectOneUserAllCollectPictures(user_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
