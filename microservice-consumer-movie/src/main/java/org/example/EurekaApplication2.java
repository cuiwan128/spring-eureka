package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaApplication2 {
    // git 测试
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication2.class);
    }
}
