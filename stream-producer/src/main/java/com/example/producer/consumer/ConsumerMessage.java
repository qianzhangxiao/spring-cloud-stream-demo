package com.example.producer.consumer;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class ConsumerMessage {

    @Bean
    Consumer<String> email(){
        return str->{
            System.out.println("邮件---》"+str);
        };
    }

    @Bean
    Consumer<String> mobile(){
        return str->{
            System.out.println("号码---》"+str);
        };
    }
}
