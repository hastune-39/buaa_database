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
@ConfigurationProperties(prefix = "picture")
public class Picture {
    int picture_id;
    int painter_id;
    String picture_address;
    String uploadTime;
    String title;

    public Picture(int picture_id, int painter_id, String picture_address, String title) {
        this.picture_id = picture_id;
        this.painter_id = painter_id;
        this.picture_address = picture_address;
        this.title = title;
    }

    public Picture(int painter_id, String picture_address, String title) {
        this.painter_id = painter_id;
        this.picture_address = picture_address;
        this.title = title;
    }
}
