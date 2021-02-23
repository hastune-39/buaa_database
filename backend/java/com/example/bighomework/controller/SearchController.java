package com.example.bighomework.controller;


import com.example.bighomework.mapper.KeywordMapper;
import com.example.bighomework.mapper.PainterMapper;
import com.example.bighomework.mapper.PictureMapper;
import com.example.bighomework.pojo.Keyword;
import com.example.bighomework.pojo.Painter;
import com.example.bighomework.pojo.Picture;
import com.example.bighomework.pojo.User;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private PainterMapper painterMapper;

    @RequestMapping(value = "select/title", method = RequestMethod.GET)
    public List<Picture> selectByTitle(@RequestParam String title) {
        try {
            return pictureMapper.selectByTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "select/keywords", method = RequestMethod.GET)
    public List<Picture> selectByKeywords(@RequestParam String keywords) {
        try {
            //1.将keywords按空格分割
            String[] keywordsList = keywords.split(" ");
            //2.每个keyword返回
            ArrayList<HashSet<Integer>> sortByKeyword = new ArrayList<>();
            HashSet<Integer> allPicturesID = new HashSet<>();
            for (int i = 0; i < keywordsList.length; i++) {
                List<Integer> selectByOneKeyword = keywordMapper.selectByOneKeyword(keywordsList[i]);
                allPicturesID.addAll(selectByOneKeyword);
                HashSet<Integer> temp = new HashSet<>();
                temp.addAll(selectByOneKeyword);
                sortByKeyword.add(temp);
            }
            //3.寻找含有所有keyword的图片id
            ArrayList<Integer> back_id = new ArrayList<>();
            for (int picture_id : allPicturesID) {
                boolean hasAllKeywords = true;
                for (HashSet<Integer> temp : sortByKeyword) {
                    if (!temp.contains(picture_id)) {
                        hasAllKeywords = false;
                        break;
                    }
                }
                if (hasAllKeywords) {
                    back_id.add(picture_id);
                }
            }
            //4.寻找所有图片
            ArrayList<Picture> back = new ArrayList<>();
            for (int picture_id : back_id) {
                back.add(pictureMapper.queryPictureByPicture_id(picture_id));
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "search/painter", method = RequestMethod.GET)
    public ResponseEntity<Boolean> ifHasPainter(@RequestParam int painter_id) {
//       System.out.println("所要查询的画师id为"+ painter_id);
        if(painterMapper.selectByID(painter_id)!=null){
           return new ResponseEntity<Boolean>(true, HttpStatus.OK);
       }
        return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
