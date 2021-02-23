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
@ConfigurationProperties(prefix = "picturesset")
public class Pictures_set {
    private int pictures_sets_id;
    private int painter_id;
    private String set_name;
    private String createTime;
    private String remarks;
    private String cover;

    public Pictures_set(int painter_id, String set_name, String createTime, String remarks, String cover) {
        this.painter_id = painter_id;
        this.set_name = set_name;
        this.createTime = createTime;
        this.remarks = remarks;
        this.cover = cover;
    }
}
