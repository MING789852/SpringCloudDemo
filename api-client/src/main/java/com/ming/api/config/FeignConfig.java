package com.ming.api.config;

import enums.FeignHeaderKey;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import util.AuthContext;

import java.nio.charset.Charset;

/**
 * 配置feign日志和请求拦截器和utf8编码
 */
@Slf4j
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                log.info("拦截微服务之间的调用");
                String url=requestTemplate.url();
                log.info("feign调用请求->{}",url);


                //微服务之间调用传递token
                requestTemplate.header(FeignHeaderKey.AUTH.toString(), AuthContext.getAuth());
            }
        };
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return new HttpMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            }
        });
    }
}
