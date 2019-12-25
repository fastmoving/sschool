package com.usoft.sschool_student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.usoft")
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.usoft.sschool_student.mapper"})
public class SschoolStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SschoolStudentApplication.class, args);
    }

}
