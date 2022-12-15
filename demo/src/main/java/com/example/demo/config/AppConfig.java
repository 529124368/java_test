package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.User;

@Configuration
public class AppConfig {
    @Bean("User")
    public User getUser() {
        return new User("lishuzheng", 31);
    }
}
