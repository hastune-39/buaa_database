package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Like;
import com.example.bighomework.pojo.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionMapper {

    @Select("select * from LSP.dbo.collections where user_id = #{user_id}")
    public List<Like> selectOneUserAllCollections(int user_id);

    @Insert("insert into LSP.dbo.collections (user_id, picture_id) " +
            "values (#{user_id}, #{picture_id})")
    public void addCollection(int user_id, int picture_id);

    @Delete("delete from LSP.dbo.collections where user_id = #{user_id} and picture_id = #{picture_id}")
    public void cancelCollection(int user_id, int picture_id);

    @Select("select * from LSP.dbo.pictures where picture_id in (select picture_id from LSP.dbo.collections where user_id = #{user_id})")
    public List<Picture> selectOneUserAllCollectPictures(int user_id);

    /***
     * 排行榜部分
     */

    @Select("select count(*) from LSP.dbo.collections where picture_id = #{picture_id}")
    public int getOnePictureCollectedTime(int picture_id);
}
