package com.myproject.ecommerce.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudiaryConfig {

    @Bean
    public Cloudinary configKey(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "djw4qdufh");
        config.put("api_key", "753934923652286");
        config.put("api_secret", "Wtwcr7MvG2NJ2o5tnPdv9tj14E0");

        return new Cloudinary(config);
    }
}
