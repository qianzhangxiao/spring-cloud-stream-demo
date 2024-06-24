package com.example.producer.producer;


import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
//3.x之前版本使用，现在已过时，官方不推荐使用
//@EnableBinding(Source.class)
public class MyMessageProducer {


    //3.x之前版本使用，现在已过时，官方不推荐使用
//    @Autowired
//    private MessageChannel output;
//    @Override
//    public String send() {
//        String serial = IdUtil.simpleUUID();
//        System.out.println("******发送了msg：" + serial);
//        output.send(MessageBuilder.withPayload(serial).build());
//        return serial;
//    }

    @Resource
    private StreamBridge streamBridge;


    public void send(String msg){
        streamBridge.send("send-out-0",msg);
        System.out.println("生产数据---》"+msg);
    }

}
