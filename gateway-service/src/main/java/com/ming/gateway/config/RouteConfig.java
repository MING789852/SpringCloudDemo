package com.ming.gateway.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * 动态路由
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RouteConfig{
    //网关路由修改器
    private final RouteDefinitionWriter routeDefinitionWriter;
    //nacos配置中心管理器
    private final NacosConfigManager nacosConfigManager;

    private final RouteProperties routeProperties;

    private String dataId="routes.json";
    private String groupId="DEFAULT_GROUP";

    //存储已存在的路由，实现先删后更新
    private Set<String> routeIdSet=new HashSet<>();

    @PostConstruct
    private void init() throws NacosException {
        if (routeProperties.isDynamic()){
            log.info("=============动态加载路由===============");
            dynamicInit();
        }else {
            log.info("=============静态加载路由===============");
            staticInit();
        }
    }

    private void staticInit(){
        String configData=getJsonFile();
        updateRoutes(configData);
    }

    private void dynamicInit() throws NacosException {
        //5秒监听一次
        String configData=nacosConfigManager.getConfigService().getConfigAndSignListener(dataId, groupId, 5000, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String newConfigData) {
                //nacos配置刷新时
                updateRoutes(newConfigData);
            }
        });

        //初次运行则加载
        updateRoutes(configData);
    }

    private void updateRoutes(String newConfigData){
        log.info("开始更新路由->{}",newConfigData);
        if (StringUtils.isNotBlank(newConfigData)){
            //先删除
            for (String routeId:routeIdSet){
                routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
            }
            routeIdSet.clear();

            //后新增
            List<RouteDefinition> routeDefinitionList=JSONUtil.toList(newConfigData, RouteDefinition.class);
            if (!CollectionUtils.isEmpty(routeDefinitionList)){
                routeDefinitionList.forEach(item->{
                    routeDefinitionWriter.save(Mono.just(item)).subscribe();
                    routeIdSet.add(item.getId());
                });
            }
        }
    }

    private String getJsonFile(){
        return FileUtil.readUtf8String(dataId);
    }
}
