package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer  //配置中心服务端配置
public class ConfigServerOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerOtherApplication.class, args);
    }

}
