## spring cloud stream
### 1、入门案例

4.1 安装rabbit 
https://blog.csdn.net/tirster/article/details/121938987
4.2 环境准备
（1）创建spring-cloud-stream-demo项目
（2）准备eureka项目（eureka-server-one、eureka-server-two）
（3）创建stream-producer项目
-  导入依赖
~~~
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
~~~
或者
~~~
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
~~~
- 完整依赖
~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example</groupId>
        <artifactId>spring-cloud-stream-demo</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>stream-producer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>stream-producer</name>
    <description>stream-producer</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

~~~


-  配置文件
~~~
server:
  port: 8001
spring:
  application:
    name: stream-producer
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: / #虚拟主机
  cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          environment:
            spring:
              rabbitmq:
                addresses: localhost # 服务器IP
                port: 5672  # 服务器端口
                username: guest # 用户名
                password: guest # 密码
      bindings: #服务的整合处理
        send-out-0: #生产者通道的名称 需和生产者 代码里面 通道名称一致
          destination: studyExchange #studyExchange  #这个名字是一个通道的名称
          content-type: application/json #设置消息类型，本次为json，文本则设置"text/plain"
eureka:
  instance:
    prefer-ip-address: true  #是否使用ip地址注册
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    service-url: #设置服务注册中心地址，集群环境
      defaultZone: http://root:123456@localhost:8761/eureka/,http://root:123456@localhost:8762/eureka/


~~~
新建MessageProducer
~~~java
package com.example.producer.producer;


import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
//3.x之前版本使用，现在已过时，官方不推荐使用
//@EnableBinding(Source.class)
public class MessageProducer {


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

~~~

（4）创建 stream-consumer项目
- 导入依赖
spring-cloud-stream-binder-rabbit
~~~
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
        </dependency>
~~~
或者
~~~
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
~~~
- 完整依赖：
~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example</groupId>
        <artifactId>spring-cloud-stream-demo</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>stream-comsumer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>stream-comsumer</name>
    <description>stream-comsumer</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

~~~
- 配置文件
~~~
server:
  port: 8002

spring:
  application:
    name: stream-consumer
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: / #虚拟主机
  cloud:
    stream:
      bindings: #服务的整合处理
        send-in-0: #消费者通道的名称
          destination: studyExchange #studyExchange  自定义一个通道的名称
          content-type: application/json #设置消息类型，本次为json，文本则设置"text/plain"
          group: ijustecA  # 自定义 分组名称
eureka:
  instance:
    prefer-ip-address: true  #是否使用ip地址注册
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    service-url: #设置服务注册中心地址，集群环境
      defaultZone: http://root:123456@localhost:8761/eureka/,http://root:123456@localhost:8762/eureka/


~~~
- consumer代码
~~~java
package com.example.comsumer.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
     * //1、在stream3.1中 我们不需要像以前一样用@Binding @StreamListener来监听了 这样少写了很多代码和配置 我们可以使用StreamBrige来进行发送
     * //2、StreamBrige.send() 方法的参数拼写规则：
     * //可以直接看官方文档[命名规则][https://docs.spring.io/spring-cloud-stream/docs/3.1.0/reference/html/spring-cloud-stream.html#_binding_and_binding_names]
     * //输入:    <方法名> + -in- + <index>
     * //输出:    <方法名> + -out- + <index>
     * //3、接收的时候直接用前面的方法名即可
     * @return
     */
    /**
     * 方法名为send，须与通道名称一致,send-in-0通道名为send
     */
    @Bean
    Consumer<String> send(){
        System.out.println(1);
        return str->{
            System.out.println("rabbitMq消息："+str);
        };
    }

}

~~~


测试消费
~~~java
package com.example.producer;

import com.example.producer.producer.MessageProducer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StreamProducerApplicationTests {


    @Resource
    private MessageProducer messageProducer;

    @Test
    void contextLoads() throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("test","hello");
        messageProducer.send(jsonObject.toString());
    }

}

~~~
结果：
![结果](https://gitee.com/newcomer_qian/pictures/raw/master/cloud/steram-result.png)

## 2、短信邮件案例
- 生产者stream-producer生成包含邮件和短信的消息
- 消费者stream-consumer消费邮件和短信消息，同时生成邮件消息和短信消息
- 生产者stream-producer分别发送邮件和短信
代码见项目

## 3、消费分组
> 如果有多个消息消费者，消息生产者发送的消息会被多个消费者都接收到，这种有很大问题。比如一个订单同时被两个服务消费。为了避免这种情况，stream提供了消息分组来解决该问题
> 在stream中处于同一个group中的多个消费者是竞争关系，能够保证消息只会被其中一个应用消费，不同组是可以消费的。通过spring.cluod.stream.bindings.<bandingName>.group属性指定组名

~~~
server:
  port: 8001
spring:
  application:
    name: stream-producer
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: /
  cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          environment:
            spring:
              rabbitmq:
                addresses: localhost # 服务器IP
                port: 5672  # 服务器端口
                username: guest # 用户名
                password: guest # 密码
      bindings:
        send-out-0:
          destination: studyExchange
          content-type: application/json
        email-in-0:
          destination: emailExchange
          content-type: application/json
          group: emailGroup  # 自定义 分组名称
        mobile-in-0:
          destination: mobileExchange
          content-type: application/json
          group: mobileGroup  # 自定义 分组名称，一条消息，每个消费者组只能有一个消费者消费到
      #消费者需配置以下方法，不然rabbitMq无法生成queue，无法消费信息
      function:
        definition: email;mobile

eureka:
  instance:
    prefer-ip-address: true  #是否使用ip地址注册
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    service-url: #设置服务注册中心地址，集群环境
      defaultZone: http://root:123456@localhost:8761/eureka/,http://root:123456@localhost:8762/eureka/
~~~

##  4、消息分区

> 通过消息分组可以解决消息被重复消费的问题，但在某些场景下分组还不能满足我们的需求。比如同时有多条同一个用户的数据发送过来，我们需要根据用户统计，但是消息被分割到了不通的集群节点上，这是我们可以考虑消息分区。
当生产者将消息发送给多个消费者时，保证同一消息始终由同一个消费者实例接收和处理。消息分区是对消息分组的一种补充。

![消息分区](https://gitee.com/newcomer_qian/pictures/raw/master/cloud/message-partition.png)

