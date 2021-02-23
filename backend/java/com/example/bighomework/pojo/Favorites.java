package com.example.bighomework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "favorites")
public class Favorites {
    int favorites_id;
    int user_id;
    String favorites_name;
    String createTime;

    public Favorites(int user_id, String favorites_name, String createTime) {
        this.user_id = user_id;
        this.favorites_name = favorites_name;
        this.createTime = createTime;
    }
}
