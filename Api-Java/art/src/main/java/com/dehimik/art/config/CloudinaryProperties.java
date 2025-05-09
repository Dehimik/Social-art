package com.dehimik.art.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {
    @Getter
    @Setter
    private String cloudName;

    @Getter
    @Setter
    private String apiKey;

    @Getter
    @Setter
    private String apiSecret;
}
