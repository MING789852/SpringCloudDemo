package com.ming.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("temp2-service")
public interface Temp2Client {

    @GetMapping("/temp2/some")
    String some();

    @GetMapping("/temp2/testSeata")
    String testXA();
}
