package com.usoft.sschool_zuul;

import com.usoft.sschool_zuul.filter.AccessFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableDiscoveryClient
@MapperScan({"com.usoft.sschool_zuul.dao"})
public class SschoolZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(SschoolZuulApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }
}
