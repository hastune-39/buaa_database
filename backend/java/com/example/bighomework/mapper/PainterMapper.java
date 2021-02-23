package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Painter;
import com.example.bighomework.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

public interface PainterMapper {

    @Insert("insert into LSP.dbo.painters (painter_id) values (#{painter_id})")
    public void insertPainter(int painter_id);

    @Select("select userID from LSP.dbo.users where userName = #{name}")
    public List<Integer> selectIDByName(String name);

    @Select("select * from LSP.dbo.users where userID=#{painter_id}")
    public User getPainterMessage(int painter_id);

    @Select("select * from LSP.dbo.users where userID=#{painter_id}")
    public User queryOnePainterInfo(int painter_id);

    /***
     * 搜索画师部分
     */

    @Select("select * from LSP.dbo.painters where painter_id = #{painter_id}")
    public Painter selectByID(int painter_id);
}
