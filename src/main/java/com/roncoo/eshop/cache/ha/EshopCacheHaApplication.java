package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.filter.HystrixRequestContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EshopCacheHaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EshopCacheHaApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>(new HystrixRequestContextFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
