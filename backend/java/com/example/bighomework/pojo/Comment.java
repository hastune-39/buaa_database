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
@ConfigurationProperties(prefix = "comment")
public class Comment {
    int comment_id;
    int commentator_id;
    int picture_id;
    String content;
    String commentTime;

    public Comment(int commentator_id, int picture_id, String content) {
        this.commentator_id = commentator_id;
        this.picture_id = picture_id;
        this.content = content;
    }
}
