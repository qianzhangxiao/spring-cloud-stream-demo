package com.example.client.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "user.info")
@Data
public class CommonConfig {

    private String name;

    private String password;

}
