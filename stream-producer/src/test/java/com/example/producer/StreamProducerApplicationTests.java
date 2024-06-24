package com.example.producer;

import com.example.producer.producer.MyMessageProducer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StreamProducerApplicationTests {


    @Resource
    private MyMessageProducer messageProducer;

    @Test
    void contextLoads() throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","qianzhagnxiao@qq.com");
        jsonObject.put("mobile","18855034935");
        messageProducer.send(jsonObject.toString());
    }

}
