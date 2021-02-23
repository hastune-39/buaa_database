package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {

    @Select("select * from LSP.dbo.pictures")
    public List<Picture> getAllPictures();


    @Insert("insert into LSP.dbo.pictures (picture_id, painter_id, picture_address, title)" +
            "values" +
            "(#{picture_id}, #{painter_id}, #{picture_address}, #{title})")
    public void insertOnePicture(Picture picture);

    /***
     * 索引查询-按照画师id
     */
    @Select("select * from LSP.dbo.pictures with(index = picture_index) where painter_id = #{painter_id}")
    public List<Picture> queryPicturesByPainter_id(int painter_id);

    @Select("select * from LSP.dbo.pictures where picture_id = #{picture_id}")
    public Picture queryPictureByPicture_id(int picture_id);

    @Select("select * from LSP.dbo.pictures where picture_id in " +
            "(select picture_id from LSP.dbo.keywords where keyword = #{keyword})")
    public List<Picture> queryPicturesByKeyword(String keyword);

    @Select("select max(picture_id) from LSP.dbo.pictures")
    public int getMaxID();

    /***
     * 画师管理图片部分
     */
    @Update("update LSP.dbo.pictures set title = #{title} where picture_id = #{picture_id}")
    public void updatePictureTitle(int picture_id, String title);

    @Delete("delete from LSP.dbo.pictures where picture_id = #{picture_id}")
    public void deletePicture(int picture_id);

    @Select("select painter_id from LSP.dbo.pictures where picture_id = #{picture_id}")
    public int selectPictureAuthorID(int picture_id);

    /***
     * picture-page
     */
    @Select("select painter_id from LSP.dbo.pictures where picture_id = #{picture_id}")
    public int getPainterIDbyPictureID(int picture_id);

    /***
     * 搜索部分
     */

    @Select("select * from LSP.dbo.pictures where title like concat('%', #{title}, '%')")
    public List<Picture> selectByTitle(String title);

    /***
     * 排行榜部分,复杂sql语句
     */
    @Select("select B.picture_id,painter_id,picture_address,uploadTime,title from \n" +
            "(select TOP(99) PERCENT picture_id from\n" +
            "((select picture_id, count(*) as browseNum from LSP.dbo.browses \n" +
            "where picture_id in (select picture_id from LSP.dbo.pictures where DateDiff(dd,uploadTime,getdate())=0)\n" +
            "group by picture_id)) as A order by browseNum desc) as B inner join LSP.dbo.pictures on LSP.dbo.pictures.picture_id= B.picture_id ")
    public List<Picture> getRankPictures();
}
