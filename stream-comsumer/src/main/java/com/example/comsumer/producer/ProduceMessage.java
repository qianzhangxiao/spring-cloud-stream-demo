package com.example.comsumer.producer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProduceMessage {

    @Resource
    private StreamBridge streamBridge;


    public void mobile(String msg){
        streamBridge.send("mobile-out-0",msg);
        System.out.println("号码发送消息——》"+msg);
    }

    public void email(String msg){
        streamBridge.send("email-out-0",msg);
        System.out.println("邮件发送消息——》"+msg);
    }
}
