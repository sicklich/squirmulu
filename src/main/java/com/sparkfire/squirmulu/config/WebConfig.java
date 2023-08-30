//package com.sparkfire.squirmulu.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 全局 CORS 配置
//        registry.addMapping("/**") // 匹配所有请求路径
//                .allowedOrigins("http://localhost:8081","http://92.168.31.169") // 允许所有来源
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
//                .allowCredentials(true) // 允许发送 Cookie
//                .maxAge(3600); // 预检请求的有效期（秒）
//
//        // 针对特定路径的 CORS 配置
//        // registry.addMapping("/api/**").allowedOrigins("http://example.com");
//    }
//}