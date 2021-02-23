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
@ConfigurationProperties(prefix = "painter")
public class Painter {
    int painter_id;
    String registeTime;
    String QR_CODE;

    public Painter(String registeTime, String QR_CODE) {
        this.registeTime = registeTime;
        this.QR_CODE = QR_CODE;
    }
}
