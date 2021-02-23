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
@ConfigurationProperties(prefix = "support")
public class Support {
    int support_id;
    int sponsor_id;
    int receiver_id;
    float amount;
    String sponsorTime;

    public Support(int sponsor_id, int receiver_id, float amount) {
        this.sponsor_id = sponsor_id;
        this.receiver_id = receiver_id;
        this.amount = amount;
    }
}
