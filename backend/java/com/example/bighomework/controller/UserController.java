package com.example.bighomework.controller;

import com.example.bighomework.mapper.PainterMapper;
import com.example.bighomework.mapper.UserMapper;
import com.example.bighomework.pojo.Painter;
import com.example.bighomework.pojo.User;
import jdk.nashorn.api.scripting.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PainterMapper painterMapper;

    @RequestMapping(value = {"/users/queryAll"}, method = RequestMethod.GET)
    public List<User> queryAllUser() {
        List<User> userList = userMapper.selectAll();
        return userList;
    }


    //begin
    @RequestMapping(value = {"/users/query"}, method = RequestMethod.GET)
    public List<String> queryIDByName(@RequestParam String name) {
        return userMapper.selectIDByName(name);
    }

    @RequestMapping(value = {"/users/query/{id}"}, method = RequestMethod.GET)
    public User queryUserByID(@PathVariable int id) {//can id be int when used in dynamic URL?
        return userMapper.selectByID(id);
    }

    //wait to be tested

    @RequestMapping(value = {"/users/getByID"}, method = RequestMethod.GET)
    public User getUserByID(@RequestParam int userID) {
        try {
            return userMapper.selectByID(userID);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserController-getUser: 出错啦!");
        }
        return new User();
    }

    @RequestMapping(value = {"/users/get"}, method = RequestMethod.GET)
    public ResponseEntity<String> getUserByLoginName(@RequestParam String login_name, String password) {
        //用户名不存在、密码错误...
        User user = userMapper.selectUserByLoginName(login_name);
        if (user == null) {
            System.out.println(login_name + "用户不存在!");
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (!password.equals(user.getPass_word())) {
            System.out.println(login_name + "用户密码错误! 错误密码: " + password);
            return new ResponseEntity<String>("", HttpStatus.NOT_IMPLEMENTED);
        } else {
            try {
                JSONObject back = new JSONObject();
                back.put("userID", user.getUserID());
                back.put("login_name", user.getUserName());
                back.put("userName", user.getUserName());
                back.put("pass_word", user.getPass_word());
                back.put("sex", user.getSex());
                back.put("address", user.getAddress());
                back.put("profile_picture", user.getProfile_picture());
                back.put("signature", user.getSignature());

                Painter painter = painterMapper.selectByID(user.getUserID());
                if (painter == null) {
                    back.put("isPainter", false);
                } else {
                    back.put("isPainter", true);
                }

                return new ResponseEntity<String>(back.toString(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //end

    @RequestMapping(value = {"users/registe"}, method = RequestMethod.POST)
    public ResponseEntity<String> registeUser(@RequestParam String login_name, String pass_word) {
        System.out.println("正在注册用户...");
        try {
            //1.先检查用户是否重新注册
            User repeateUser = userMapper.selectUserByLoginName(login_name);
            if (repeateUser != null) {
                //重复登陆
                return new ResponseEntity<String>("repeate!", HttpStatus.BAD_REQUEST);
            }
            //2.否则成功插入
            User user = new User(login_name, pass_word);
            userMapper.insertUser(user);
            return new ResponseEntity<String>("success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("fail!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam int userID) {
        try {
            userMapper.deleteUser(userID);
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail!";
        }
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String upadteUser(@RequestBody String json) {
        try {
            JSONObject userparams = new JSONObject(json);
            int userID = userparams.getInt("userID");//约定: 必须传userID
            if (userparams.has("userName")) {
                userMapper.updateUserName(userID, userparams.getString("userName"));
            } else if (userparams.has("address")) {
                userMapper.updateUserAddress(userID, userparams.getString("address"));
            } else if (userparams.has("signature")) {
                userMapper.updateUserSignature(userID, userparams.getString("signature"));
            } else if (userparams.has("profile_picture")) {
                userMapper.updateUserSignature(userID, userparams.getString("profile_picture"));
            } else {
                System.out.println("UserController-updateUser: 错误的参数类型");
            }
            System.out.println("更新用户信息成功!");
            return "success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "update fail!";
        }
    }
}
