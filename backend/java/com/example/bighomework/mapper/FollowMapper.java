package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Follow;
import com.example.bighomework.pojo.Picture;
import com.example.bighomework.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Repository
public interface FollowMapper {
    @Select("select * from LSP.dbo.follows where follower_id = #{user_id}")
    public List<Follow> selectOneUserFollows(int user_id);

    @Select("select * from LSP.dbo.users where painter_id = #{painter_id}")
    public User selectPainterMessage(int painter_id);

    @Select("select * from LSP.dbo.users where userID in" +
            "(select painter_id from LSP.dbo.follows where follower_id = #{user_id}) " +
            "order by userID")
    public List<User> selectOneUserFollowedPainters(int user_id);

    @Select("select * from LSP.dbo.pictures where painter_id in " +
            "(select painter_id from LSP.dbo.follows where follower_id = 1) " +
            "order by painter_id")
    public List<Picture> selectAllFollowPictures(int user_id);

    /***
     * 图片-索引查询-按照画师id
     */
    @Select("select * from LSP.dbo.pictures with(index = picture_index) where painter_id = #{painter_id}")
    public List<Picture> selectOnePainterPictures(int painter_id);

    @Select("select * from LSP.dbo.follows where follower_id = #{follower_id} and painter_id = #{painter_id}")
    public Follow selectFollowStatus(int follower_id, int painter_id);

    @Insert("insert into LSP.dbo.follows (follower_id, painter_id) values (#{follower_id}, #{painter_id})")
    public void addFollow(int follower_id, int painter_id);

    @Delete("delete from LSP.dbo.follows where follower_id =#{follower_id} and painter_id = #{painter_id}")
    public void cacelFollow(int follower_id, int painter_id);

    /***
     * picture-page
     */
    @Select("select * from LSP.dbo.follows where follower_id = #{follower_id} and painter_id = #{painter_id}")
    public Follow ifHasFollow(int follower_id, int painter_id);
}