package com.usoft.sschool_teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("com.usoft")
//@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.usoft.sschool_teacher.mapper")
//@MapperScan({"com.usoft.sschool_manage.mapper.base","com.usoft.sschool_manage.mapper.personal"})
public class SschoolTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SschoolTeacherApplication.class, args);
    }

}
