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
@ConfigurationProperties(prefix = "follow")
public class Follow {
    int follower_id;
    int painter_id;
    String followTime;

    public Follow(int follower_id, int painter_id) {
        this.follower_id = follower_id;
        this.painter_id = painter_id;
    }
}
