package com.ming.temp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients()
@SpringBootApplication
public class Temp2Application {
    public static void main(String[] args) {
        SpringApplication.run(Temp2Application.class,args);
    }
}
