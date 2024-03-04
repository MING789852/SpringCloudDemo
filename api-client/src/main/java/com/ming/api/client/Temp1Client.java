package com.ming.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("temp1-service")
public interface Temp1Client {

    @GetMapping("/temp1/some")
    String some();
}
