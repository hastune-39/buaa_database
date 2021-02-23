package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Browse;
import com.example.bighomework.pojo.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BrowseMapper {
    @Select("select * from LSP.dbo.browses")
    public List<Browse> selectAll();

    @Insert("insert into LSP.dbo.browses " +
            "(picture_id, browser_id) " +
            "values " +
            "(#{picture_id}, #{browser_id})")
    public void insert(Browse browse);

    @Delete("delete from LSP.dbo.browse where browse_id = #{browse_id}")
    public void delete(int browse_id);

    @Update("update LSP.dbo. set " +
            "picture_id = #{picture_id}, browser_id = #{browser_id}" +
            " where picture_id=#{picture_id}")
    public void update(Browse browse);//need trigger to change time

    /***
     * 排行榜部分
     */

    @Select("select count(*) from LSP.dbo.browses where picture_id = #{picture_id}")
    public int getOnePictureBrowseTime(int picture_id);

    /***
     * 跳转部分
     */

    @Insert("insert into LSP.dbo.browses (picture_id, browser_id)\n" +
            "values\n" +
            "(#{picture_id},#{user_id})\n")
    public void addPictureBrowse(int user_id, int picture_id);

    /***
     * 浏览历史部分
     */
    @Select("select history.picture_id, painter_id, picture_address, uploadTime, title from LSP.dbo.pictures inner join\n" +
            "(select TOP(100) picture_id from LSP.dbo.browses where browser_id = #{user_id} group by picture_id order by max(browseTime) desc) as history\n" +
            "on LSP.dbo.pictures.picture_id = history.picture_id")
    public List<Picture> getHistoryPictures(int user_id);
}
