package com.example.bighomework.controller;

import com.example.bighomework.mapper.BrowseMapper;
import com.example.bighomework.pojo.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrowseController {
    @Autowired
    private BrowseMapper browseMapper;

    @RequestMapping(value = "/History", method = RequestMethod.GET)
    public List<Picture> getHistoryPictures(@RequestParam int user_id) {
        try {
            return browseMapper.getHistoryPictures(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
