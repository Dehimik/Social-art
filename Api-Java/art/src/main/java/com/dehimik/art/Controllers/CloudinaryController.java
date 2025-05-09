package com.dehimik.art.Controllers;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CloudinaryController {
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/cloudinary/signature")
    public Map<String, String> getSignature() {
        long timestamp = System.currentTimeMillis() / 1000;
        String signature = cloudinary.apiSignRequest(
                Map.of("timestamp", String.valueOf(timestamp)),
                cloudinary.config.apiSecret
        );

        return Map.of(
                "timestamp", String.valueOf(timestamp),
                "signature", signature,
                "apiKey", cloudinary.config.apiKey,
                "cloudName", cloudinary.config.cloudName
        );
    }
}

