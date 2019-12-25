package com.usoft.sschool_pub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.usoft.sschool_pub.mapper"})
public class SschoolPubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SschoolPubApplication.class, args);
    }

}

