package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    @Select("select * from LSP.dbo.comments where picture_id = #{picture_id}")
    public List<Comment> getCommentsByPictureID(int picture_id);

    @Insert("insert into LSP.dbo.comments " +
            "(commentator_id, picture_id, content) " +
            "values " +
            "(#{commentator_id}, #{picture_id}, #{content})")
    public void uploadComment(int commentator_id, int picture_id, String content);

    @Delete("delete from LSP.dbo.comments where comment_id = #{comment_id}")
    public void deleteComment(int comment_id);
}
