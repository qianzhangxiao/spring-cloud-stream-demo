package com.example.eureka.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @description: 安全认证配置类
 * @author: qc
 * @time: 2021/8/1 18:01
 */
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*方案一、CSRF忽略/eureka/** 的所有请求*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);  //加这句是为了访问eureka控制台和 /actuator时能做到安全控制
        http.csrf().ignoringAntMatchers("/eureka/**"); //忽略eureka/** 的所有请求
    }
}
