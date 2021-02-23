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
@ConfigurationProperties(prefix = "browse")
public class Browse {
    int browse_id;
    int picture_id;
    int browser_id;
    String browseTime;

    public Browse(int picture_id, int browser_id) {
        this.picture_id = picture_id;
        this.browser_id = browser_id;
    }
}
