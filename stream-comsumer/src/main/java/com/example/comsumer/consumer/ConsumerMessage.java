package com.example.comsumer.consumer;

import com.alibaba.fastjson.JSON;
import com.example.comsumer.producer.ProduceMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.MessageProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
//3.x之前版本使用，现在已过时，官方不推荐使用
//@EnableBinding(Sink.class)
public class ConsumerMessage {


//3.x之前版本使用，现在已过时，官方不推荐使用
//    @StreamListener(Sink.INPUT)
//    public void input(Message<String> message){
//        System.out.println("我是消费者--------->: "+message.getPayload() + "\t port: " + serverPort);
//    }

    /**
     * //这里接收rabbitmq的条件是参数为Consumer 并且 方法名和supplier方法名相同
     * //这里的返回值是一个匿名函数 返回类型是consumer 类型和提供者的类型一致
     * //supplier发送的exchange是 send-in-0 这里只需要用send方法名即可
     * //1、在stream3.1中 我们不需要像以前一样用@Binding @StreamListener来监听了 这样少写了很多代码和配置 我们可以使用StreamBridge来进行发送
     * //2、StreamBridge.send() 方法的参数拼写规则：
     * //可以直接看官方文档[命名规则][https://docs.spring.io/spring-cloud-stream/docs/3.1.0/reference/html/spring-cloud-stream.html#_binding_and_binding_names]
     * //输入:    <方法名> + -in- + <index>
     * //输出:    <方法名> + -out- + <index>
     * //3、接收的时候直接用前面的方法名即可
     * @return
     */
    /**
     * 方法名为send，须与通道名称一致,send-in-0通道名为send
     */
    @Resource
    private ProduceMessage produceMessage;

    @Bean
    Consumer<String> send(){
        return str->{
            Map<String,Object> map = JSON.parseObject(str,Map.class) ;
            Map<String,Object> email=new HashMap<>();
            Map<String,Object> mobile=new HashMap<>();
            mobile.put("mobile",map.get("mobile"));
            email.put("email",map.get("email"));
            produceMessage.email(JSON.toJSONString(email));
            produceMessage.mobile(JSON.toJSONString(mobile));
        };
    }

}
