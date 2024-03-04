package com.ming.gateway.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.ming.gateway.config.AuthProperties;
import enums.FeignHeaderKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 继承Ordered接口，确保自定义优先级比NettyRoutingFilter高
 */
@Component
@RequiredArgsConstructor
public class AuthGlobeFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher=new AntPathMatcher();

    private final AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断是否允许通过
        ServerHttpRequest serverHttpRequest=exchange.getRequest();
        String path=serverHttpRequest.getPath().toString();
        if (isAuthPath(path)){
            //放行
            return chain.filter(exchange);
        }

        //简单判断参数是否含有?auth=xxx,有则通过，无则返回无授权提示
        List<String> authList=serverHttpRequest.getQueryParams().get("auth");


        if (CollectionUtils.isEmpty(authList)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            return response.setComplete();
        }else {
            String auth=authList.get(0);
            //修改请求头信息
            ServerWebExchange newExchange=exchange.mutate().request(builder -> {
                builder
                        .header(FeignHeaderKey.GATEWAY.toString(),"gateway message")
                        .header(FeignHeaderKey.AUTH.toString(),auth);
            }).build();
            //放行
            return chain.filter(newExchange);
        }
    }


    private boolean isAuthPath(String path){
        if (!StringUtils.isEmpty(path)&&!CollectionUtils.isEmpty(authProperties.getPathList())){
            for (String authPath:authProperties.getPathList()){
               if (antPathMatcher.match(authPath,path)){
                   return true;
               }
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
