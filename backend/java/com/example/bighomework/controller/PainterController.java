package com.example.bighomework.controller;

import com.example.bighomework.mapper.PainterMapper;
import com.example.bighomework.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/painters")
public class PainterController {

    @Autowired
    private PainterMapper painterMapper;

    @RequestMapping(value = "/painters/registe", method = RequestMethod.POST)
    public String registe(@RequestParam int userID) {
        try {
            painterMapper.insertPainter(userID);
            System.out.println("painter registe successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "yes";//?? 400?
    }

    @RequestMapping(value = "painters/search", method = RequestMethod.GET)
    public List<Integer> search(@RequestParam String name) {
        List<Integer> nameList = painterMapper.selectIDByName(name);
        return nameList;
    }

    @RequestMapping(value = "painters/{painter_id}", method = RequestMethod.GET)
    public User view(@PathVariable int painter_id) {
        User user = painterMapper.getPainterMessage(painter_id);
        return user;
    }

    /***
     @RequestMapping(value = "/painters/{painter_id}/setQR")
     public String setQR(@PathVariable int painter_id, @RequestParam String QR_CODE){

     }
     **/
    //成为画师
    @RequestMapping(value = "/painter/register", method = RequestMethod.POST)
    public String becomePainter(@RequestBody String params) {
        try {
            JSONObject request = new JSONObject(params);
            int user_id = request.getInt("user_id");
            painterMapper.insertPainter(user_id);
            return "painter-register-success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "painter-register-fail!";
    }

}
