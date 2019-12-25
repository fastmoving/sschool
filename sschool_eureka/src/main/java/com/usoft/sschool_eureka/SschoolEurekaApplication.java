package com.usoft.sschool_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaServer
@SpringBootApplication
public class SschoolEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SschoolEurekaApplication.class, args);
    }

}
