package com.example.bighomework.controller;

import com.example.bighomework.mapper.BrowseMapper;
import com.example.bighomework.mapper.CollectionMapper;
import com.example.bighomework.mapper.PictureMapper;
import com.example.bighomework.mapper.UserMapper;
import com.example.bighomework.pojo.Browse;
import com.example.bighomework.pojo.Picture;
import com.example.bighomework.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankController {
    @Autowired
    private BrowseMapper browseMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private UserMapper userMapper;


    @RequestMapping(value = "Rank/allPictures", method = RequestMethod.GET)
    public List<Picture> getRankPictures() {
        try {
            List<Picture> res = pictureMapper.getRankPictures();
            System.out.println("排行榜图片个数为:" + res.size());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "Rank/Winner", method = RequestMethod.GET)
    public String getWinnerMessage(@RequestParam int picture_id) {
        try {
            JSONObject back = new JSONObject();

            Picture picture = pictureMapper.queryPictureByPicture_id(picture_id);
            back.put("address", picture.getPicture_address());
            back.put("title", picture.getTitle());

            int browseTime = browseMapper.getOnePictureBrowseTime(picture_id);
            back.put("browseTime", browseTime);

            int collectTime = collectionMapper.getOnePictureCollectedTime(picture_id);
            back.put("collectTime", collectTime);

            User painter = userMapper.selectByID(picture.getPainter_id());
            back.put("painter_id", painter.getUserID());
            back.put("painter_profile_picture", painter.getProfile_picture());

            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-winner-msg-fail!";
    }
}
