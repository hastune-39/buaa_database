package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Keyword;
import com.example.bighomework.pojo.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KeywordMapper {

    @Insert("insert into LSP.dbo.keywords values(#{picture_id}, #{keyword})")
    public void insertKeyWord(Keyword keyword);

    @Delete("delete from LSP.dbo.keywords where picture_id = #{picture_id}")
    public void deleteOnePictureKeyWords(int picture_id);

    @Select("select * from LSP.dbo.keywords where picture_id = #{picture_id}")
    public List<Keyword> getOnePictureKeywords(int picture_id);

    /***
     * 索引查询-按照关键字分组
     */
    @Select("select picture_id from LSP.dbo.keywords " +
            "with(index = keywords_index) " +
            "where keyword=#{keyword}")
    public List<Integer> selectByOneKeyword(String keyword);
}
