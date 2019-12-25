package com.usoft.sschool_manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@MapperScan({"com.usoft.sschool_manage.mapper.base","com.usoft.sschool_manage.mapper.personal"})
public class SschoolManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SschoolManageApplication.class, args);
    }

}
