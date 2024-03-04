package com.ming.temp1.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.api.client.Temp2Client;
import com.ming.temp1.domain.entity.TempUser;
import com.ming.temp1.mapper.TempUserMapper;
import com.ming.temp1.service.ITestService;
import enums.FeignHeaderKey;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/temp1")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final Temp2Client temp2Client;

    private final ITestService testService;


    @GetMapping("/some")
    public String some(HttpServletRequest request){
        String auth=request.getHeader(FeignHeaderKey.AUTH.toString());
        log.info("携带token->{}",auth);
        return "test1->11111111";
    }

    @GetMapping("/request")
    public String request(HttpServletRequest request){
        String auth=request.getHeader(FeignHeaderKey.AUTH.toString());
        log.info("携带token->{}",auth);
        String temp2Return=temp2Client.some();
        String msg=String.format("temp2服务返回->%s",temp2Return);
        log.info(msg);
        return msg;
    }


    /**
     * AT和XA模式，到shared-seata.yaml切换
     */
    @GetMapping("/unAuth/testSeata")
    public void testSeata(){
        testService.testSeata();
    }
}
