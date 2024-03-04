package com.ming.temp2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/one")
public class OneController {

    @GetMapping("temp1")
    private String temp1(){
        return "temp1";
    }


    @GetMapping("temp2")
    private String temp2(){
        return "temp1";
    }
}
