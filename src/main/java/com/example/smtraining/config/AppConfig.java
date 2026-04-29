package com.example.smtraining.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AppConfig
 *
 * Spring MVC Java Config 설정 클래스.
 *
 * XML 설정 대신 Java 클래스로 Spring MVC 설정을 한다.
 *
 * 역할:
 * 1. @Controller, @Service, @Repository 같은 Bean을 스캔
 * 2. Spring MVC 활성화
 * 3. JSP View Resolver 설정
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.smtraining")
public class AppConfig implements WebMvcConfigurer {

    /**
     * Controller에서 "MarketView"라는 문자열을 반환하면
     * 실제 JSP 경로로 변환해준다.
     *
     * 예:
     * return "MarketView";
     *
     * 변환:
     * /WEB-INF/views/MarketView.jsp
     */
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
}