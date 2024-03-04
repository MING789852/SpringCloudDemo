package com.ming.temp1;

import com.ming.api.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Feign调用路径com.ming.api
//Feign配置
@EnableFeignClients(basePackages = "com.ming.api",defaultConfiguration = FeignConfig.class)
@SpringBootApplication
public class Temp1Application {
    public static void main(String[] args) {
        SpringApplication.run(Temp1Application.class,args);
    }
}
