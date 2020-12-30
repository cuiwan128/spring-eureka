package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.mapper")
public class EurekaApplication1 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication1.class);
    }
}
