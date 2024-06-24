package com.example.comsumer;

import com.alibaba.fastjson.JSON;
import com.example.comsumer.producer.ProduceMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class StreamComsumerApplicationTests {

    @Resource
    private ProduceMessage produceMessage;

    @Test
    void contextLoads() {

        Map<String,Object> map=new HashMap<>();
        map.put("email","test@qq.com");
        produceMessage.mobile(JSON.toJSONString(map));
    }

}
