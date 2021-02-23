package com.example.bighomework.controller;

import com.example.bighomework.mapper.FollowMapper;
import com.example.bighomework.mapper.PainterMapper;
import com.example.bighomework.pojo.Follow;
import com.example.bighomework.pojo.Painter;
import com.example.bighomework.pojo.Picture;
import com.example.bighomework.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FollowController {
    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private PainterMapper painterMapper;

    //获取某一用户关注的画师id
    @RequestMapping(value = "/User/Follow", method = RequestMethod.GET)
    public java.lang.String getFollowingPaintersID(@RequestParam int user_id) {
        try {
            System.out.println("正在获取用户" + user_id + "的关注列表...");
            //提问：mapper怎么返回pojo里没有的list<>;
            List<Follow> followList = followMapper.selectOneUserFollows(user_id);
            ArrayList<JSONObject> back = new ArrayList<>();
            for (Follow follow : followList) {
                JSONObject temp = new JSONObject();
                temp.put("painter_id", follow.getPainter_id());
                temp.put("followTime", follow.getFollowTime());
                temp.put("status", true);
                back.add(temp);
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取用户关注的所有画师信息
    @RequestMapping(value = "/User/Follow/getPainters", method = RequestMethod.GET)
    public java.lang.String getAllFollowingPainterInfo(@RequestParam int user_id) {
        try {
            System.out.println("正在搜集用户关注的画师信息...");
            List<User> PainterList = followMapper.selectOneUserFollowedPainters(user_id);
            ArrayList<JSONObject> back = new ArrayList<JSONObject>();
            for (User user : PainterList) {
                JSONObject temp = new JSONObject();
                temp.put("painter_id", user.getUserID());
                temp.put("userName", user.getUserName());
                temp.put("address", user.getAddress());
                temp.put("signature", user.getSignature());
                temp.put("profile_picture", user.getProfile_picture());
                back.add(temp);
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取用户所有关注画师的图片(用于展示)
    @RequestMapping(value = "/User/Follow/getPictures", method = RequestMethod.GET)
    public String getAllFollowPainterPictures(@RequestParam int user_id) {
        try {
            System.out.println("正在搜集用户关注画师的图片...");
            List<User> PainterList = followMapper.selectOneUserFollowedPainters(user_id);
            JSONArray back = new JSONArray();//最外层,picturesInfo的[]
            for (User painter : PainterList) {
                List<Picture> pictureList = followMapper.selectOnePainterPictures(painter.getUserID());
                //picturesInfo的{}
                JSONObject temp = new JSONObject();
                temp.put("painter_id", painter.getUserID());//第一层的painter_id

                JSONArray backPictureList = new JSONArray();//第一层的pictures与第二层的[]
                for (Picture picture : pictureList) {
                    JSONObject intemp = new JSONObject();//第二层,{}
                    intemp.put("picture_id", picture.getPicture_id());
                    intemp.put("picture_address", picture.getPicture_address());
                    backPictureList.put(intemp);
                }
                //添加名pictures: []
                temp.put("pictures", backPictureList);

                //picturesInfo[] 添加对象
                back.put(temp);
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取一个画师的信息
    @RequestMapping(value = "/User/getPainterInfo", method = RequestMethod.GET)
    public String getOnePainterInfo(@RequestParam int painter_id) {
        try {
            System.out.println("正在获取画师" + painter_id + "的信息...");
            User painter = painterMapper.queryOnePainterInfo(painter_id);
            JSONObject back = new JSONObject();
            back.put("painter_id", painter.getUserID());
            back.put("userName", painter.getUserName());
            back.put("address", painter.getAddress());
            back.put("signature", painter.getSignature());
            back.put("profile_picture", painter.getProfile_picture());
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取一个画师的图片信息(用于用户点开画师页)
    @RequestMapping(value = "/User/getPainterPicturesInfo", method = RequestMethod.GET)
    public String getOnePainterPicturesInfo(@RequestParam int painter_id) {
        try {
            System.out.println("正在获取画师" + painter_id + "的图片信息...");

            JSONObject back = new JSONObject();
            back.put("painter_id", painter_id);

            List<Picture> pictures = followMapper.selectOnePainterPictures(painter_id);
            JSONArray pictureArray = new JSONArray();
            for (Picture each : pictures) {
                JSONObject temp = new JSONObject();
                temp.put("picture_id", each.getPicture_id());
                temp.put("picture_address", each.getPicture_address());
                pictureArray.put(temp);
            }

            back.put("pictures", pictureArray);

            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关注/取关
     */
    @RequestMapping(value = "follow/get", method = RequestMethod.GET)
    public String getFollowStatus(@RequestParam int user_id, @RequestParam int painter_id) {
        try {
            System.out.println("正在查找用户"+user_id+"对画师"+painter_id+"的关注状态");
            Follow follow = followMapper.selectFollowStatus(user_id, painter_id);
            JSONObject back = new JSONObject();
            if (follow == null) {
                System.out.println("无");
                back.put("status", false);
            } else {
                System.out.println("有");
                back.put("status", true);
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-follow-status-fail!";
    }

    @RequestMapping(value = "/User/AddFollow", method = RequestMethod.POST)
    public String addFollow(@RequestBody String params) {
        try {
            JSONObject data = new JSONObject(params);
            int follower_id = data.getInt("follower_id");
            int painter_id = data.getInt("painter_id");
            System.out.println("用户" + follower_id + "正在关注画师" + painter_id + "...");
            followMapper.addFollow(follower_id, painter_id);
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false!";
    }

    @RequestMapping(value = "/User/CancelFollow", method = RequestMethod.POST)
    public String cancelFollow(@RequestBody String params) {
        try {
            JSONObject data = new JSONObject(params);
            int follower_id = data.getInt("follower_id");
            int painter_id = data.getInt("painter_id");
            System.out.println("用户" + follower_id + "正在取关画师" + painter_id + "...");
            followMapper.cacelFollow(follower_id, painter_id);
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false!";
    }
}
