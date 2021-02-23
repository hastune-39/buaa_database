package com.example.bighomework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userID;
    private String login_name;
    private String userName;
    private String pass_word;
    private String sex;
    private String address;
    private String profile_picture;
    private String signature; //text -> String?

    public User(String login_name,  String pass_word) {
        this.login_name = login_name;
        this.pass_word = pass_word;
    }

    public User(String login_name, String userName, String pass_word, String sex, String address, String profile_picture, String signature) {
        this.login_name = login_name;
        this.userName = userName;
        this.pass_word = pass_word;
        this.sex = sex;
        this.address = address;
        this.profile_picture = profile_picture;
        this.signature = signature;
    }
}
