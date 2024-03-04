package com.ming.temp2.controller;

import com.ming.temp2.service.ITestService;
import enums.FeignHeaderKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/temp2")
public class TestController {

    private final ITestService testService;

    @GetMapping("/some")
    public String some(HttpServletRequest request){
        String header= request.getHeader(FeignHeaderKey.FEIGN.toString());
        log.info("temp1使用feign调用时，feign拦截器加塞请求头->{}",header);
        String auth=request.getHeader(FeignHeaderKey.AUTH.toString());
        log.info("携带token->{}",auth);
        return "test1->2222222";
    }


    @GetMapping("/unAuth")
    public String unAuth(){
        log.info("无授权测试");
        return "无授权测试";
    }

    @GetMapping("/testSeata")
    @Transactional
    public void testSeata(){
        testService.testSeata();
    }
}
