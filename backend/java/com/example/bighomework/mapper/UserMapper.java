package com.example.bighomework.mapper;

import com.example.bighomework.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from LSP.dbo.users")
    public List<User> selectAll();

    @Select("select * from LSP.dbo.users where userID=#{userID}")
    public User selectByID(int userID);

    @Select("select userName from LSP.dbo.users where userName = #{userName}")
    public List<String>  selectIDByName(String userID);

    @Select("select * from LSP.dbo.users where login_name = #{login_name}")
    public User selectUserByLoginName(String login_name);

    @Insert("insert into LSP.dbo.users " +
            "(login_name,  pass_word) " +
            "values " +
            "(#{login_name}, #{pass_word})")
    public void insertUser(User user);

    @Delete("delete from LSP.dbo.users where userID=#{userID}")
    public void deleteUser(int userID);

    @Update("update LSP.dbo.users set " +
            "login_name=#{login_name}, userName=#{userName}, pass_word=#{pass_word} , sex=#{sex}, " +
            "address=#{address}, profile_picture=#{profile_picture}, signature=#{signature}" +
            " where userID=#{userID}")
    public void updateUser(User user);

    @Update("update LSP.dbo.users set userName = #{userName} where userID = #{userID}")
    public void updateUserName(int userID, String userName);
    @Update("update LSP.dbo.users set address = #{address} where userID = #{userID}")
    public void updateUserAddress(int userID, String address);
    @Update("update LSP.dbo.users set signature = #{signature} where userID = #{userID}")
    public void updateUserSignature(int userID, String signature);
    @Update("update LSP.dbo.users set profile_picture = #{profile_picture} where userID = #{userID}")
    public void updateProfilePicture(int userID, String profile_picture);
}
