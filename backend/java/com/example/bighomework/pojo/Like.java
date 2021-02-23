package com.example.bighomework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private int user_id;
    private int picture_id;
    private String collectTime;
}
