package com.example.bighomework.controller;

import com.example.bighomework.mapper.*;
import com.example.bighomework.pojo.*;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.util.List;

@RestController
public class PicturePageController {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private Comment_likeMapper comment_likeMapper;

    @RequestMapping(value = "Picture/Message", method = RequestMethod.GET)
    public String getPictureMessage(@RequestParam int picture_id) {
        try {
            Picture picture = pictureMapper.queryPictureByPicture_id(picture_id);
            JSONObject back = new JSONObject();
            back.put("address", picture.getPicture_address());
            back.put("title", picture.getTitle());
            back.put("uploadTime", picture.getUploadTime());
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-picture-message-false!";
    }

    @RequestMapping(value = "Picture/Keywords", method = RequestMethod.GET)
    public String getPictureKeywords(@RequestParam int picture_id) {
        try {
            List<Keyword> keywords = keywordMapper.getOnePictureKeywords(picture_id);
            JSONArray back = new JSONArray();
            for (Keyword item : keywords) {
                back.put(item.getKeyword());
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-picture-keywords-false!";
    }

    @RequestMapping(value = "Picture/PainterMessage", method = RequestMethod.GET)
    public String getPicturePainterMessage(@RequestParam int picture_id) {
        try {
            int painter_id = pictureMapper.getPainterIDbyPictureID(picture_id);
            User painter = userMapper.selectByID(painter_id);
            JSONObject back = new JSONObject();
            back.put("painter_id", painter.getUserID());
            back.put("painterName", painter.getUserName());
            back.put("address", painter.getAddress());
            back.put("profile_picture", painter.getProfile_picture());
            back.put("signature", painter.getSignature());
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "picture-painter-message-false!";
    }

    @RequestMapping(value = "Picture/FollowMessage", method = RequestMethod.GET)
    public String getFollowMessage(@RequestParam("user_id") int user_id,
                                   @RequestParam("painter_id") int painter_id) {
        try {
            Follow follow = followMapper.ifHasFollow(user_id, painter_id);
            JSONObject back = new JSONObject();
            if (follow == null) {
                back.put("status", "false");
            } else {
                back.put("status", "true");
            }
            return back.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "picture-follow-message-fail!";
    }

    @RequestMapping(value = "Picture/OtherPictures", method = RequestMethod.GET)
    public String getPainterOtherPictures(@RequestParam("picture_id") int picture_id,
                                          @RequestParam("painter_id") int painter_id) {
        try {
            List<Picture> pictures = pictureMapper.queryPicturesByPainter_id(painter_id);
            JSONArray otherPictures = new JSONArray();
            for (Picture picture : pictures) {
                if (picture.getPicture_id() != picture_id) {
                    JSONObject item = new JSONObject();
                    item.put("picture_id", picture.getPicture_id());
                    item.put("address", picture.getPicture_address());
                    otherPictures.put(item);
                }
            }
            return otherPictures.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-painter-other-pictures-fail!";
    }

    @RequestMapping(value = "Picture/Comments", method = RequestMethod.GET)
    public String getPictureComments(@RequestParam int user_id, @RequestParam int picture_id) {
        try {
            List<Comment> commentList = commentMapper.getCommentsByPictureID(picture_id);
            JSONArray comments = new JSONArray();

            for (int i = commentList.size() - 1; i >= 0; i--) {
                Comment comment = commentList.get(i);

                JSONObject item = new JSONObject();

                JSONObject comment_message = new JSONObject();
                comment_message.put("comment_id", comment.getComment_id());
                comment_message.put("content", comment.getContent());
                comment_message.put("commentTime", comment.getCommentTime());
                List<Comment_like> comment_likes = comment_likeMapper.getOneCommentLikeSum(comment.getComment_id());
                comment_message.put("commentLikeTimes", comment_likes.size());
                Comment_like status = comment_likeMapper.getOneCommentLikeStatus(user_id, comment.getComment_id());
                if(status == null)comment_message.put("LikeStatus", false);
                else comment_message.put("like_status", true);


                item.put("comment_message", comment_message);

                JSONObject commentator_message = new JSONObject();
                User commentator = userMapper.selectByID(comment.getCommentator_id());
                commentator_message.put("user_id", commentator.getUserID());
                commentator_message.put("user_name", commentator.getUserName());
                commentator_message.put("profile_picture", commentator.getProfile_picture());

                item.put("commentator_message", commentator_message);

                comments.put(item);
            }

            return comments.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get-picture-comments-fail!";
    }

    @RequestMapping(value = "Picture/Comment/Upload", method = RequestMethod.POST)
    public String uploadComment(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int commentator_id = request.getInt("commentator_id");
            int picture_id = request.getInt("picture_id");
            String content = request.getString("content");
            System.out.println("用户" + commentator_id + "正在对图片" + picture_id + "发表评论");
            commentMapper.uploadComment(commentator_id, picture_id, content);
            return "upload-comment-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "upload-comment-fail!";
    }

    @RequestMapping(value = "Picture/Comment/Delete", method = RequestMethod.POST)
    public String deleteComment(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int comment_id = request.getInt("comment_id");
            commentMapper.deleteComment(comment_id);
            return "delete-comment-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "delete-comment-fail!";
    }

//    @RequestMapping(value = "Comment/LikeStatus", method = RequestMethod.GET)
//    public String getCommentLikeStatus(@RequestParam("user_id") int user_id,
//                                       @RequestParam("picture_id") int picture_id){
//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "get-comment-like-status-fail!";
//    }

    @RequestMapping(value = "Comment/Thumb", method = RequestMethod.POST)
    public String thumb(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int user_id = request.getInt("user_id");
            int comment_id = request.getInt("comment_id");
            comment_likeMapper.addCommentLike(user_id, comment_id);

            return "thumb-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "thumb-fail!";
    }

    @RequestMapping(value = "Comment/CancelThumb", method = RequestMethod.POST)
    public String cancelThumb(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int user_id = request.getInt("user_id");
            int comment_id = request.getInt("comment_id");
            comment_likeMapper.cancelCommentLike(user_id, comment_id);

            return "thumb-success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "thumb-fail!";
    }

}
