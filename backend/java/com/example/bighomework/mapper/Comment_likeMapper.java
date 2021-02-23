package com.example.bighomework.mapper;

import com.example.bighomework.pojo.Comment_like;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Comment_likeMapper {

    @Select("select * from LSP.dbo.comment_likes where comment_id = #{comment_id}")
    public List<Comment_like> getOneCommentLikeSum(int comment_id);

    @Select("select * from LSP.dbo.comment_likes " +
            "where liker_id = #{user_id} and comment_id = #{comment_id}")
    public Comment_like getOneCommentLikeStatus(int user_id, int comment_id);

    @Insert("insert into LSP.dbo.comment_likes " +
            "values " +
            "(#{comment_id}, #{user_id})")
    public void addCommentLike(int user_id, int comment_id);

    @Delete("delete from LSP.dbo.comment_likes " +
            "where liker_id = #{user_id} and comment_id = #{comment_id}")
    public void cancelCommentLike(int user_id, int comment_id);
}
