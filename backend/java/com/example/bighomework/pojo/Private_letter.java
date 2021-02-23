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
@ConfigurationProperties(prefix = "privateletter")
public class Private_letter {
    int private_letter_id;
    int sender_id;
    int receiver_id;
    String content;
    String sendTime;

    public Private_letter(int sender_id, int receiver_id, String content) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content = content;
    }
}
