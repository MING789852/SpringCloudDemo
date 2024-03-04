package com.ming.temp1.service.impl;

import com.ming.api.client.Temp2Client;
import com.ming.temp1.domain.entity.TempUser;
import com.ming.temp1.mapper.TempUserMapper;
import com.ming.temp1.service.ITestService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService implements ITestService {

    private final TempUserMapper tempUserMapper;

    private final Temp2Client temp2Client;

    @Override
    @GlobalTransactional
    public void testSeata() {
        Boolean rollback=true;

        String xid = RootContext.getXID();
        log.info("create order in transaction: " + xid);

        TempUser tempUser=tempUserMapper.selectById(1);
        TempUser update=new TempUser();
        update.setId(tempUser.getId());
        update.setPoint(tempUser.getPoint()-1);
        tempUserMapper.updateById(update);

        try {
            temp2Client.testXA();
        }catch (Exception e){
            throw new RuntimeException("temp2 rollback ... ");
        }

        if (rollback) {
            throw new RuntimeException("Force rollback ... ");
        }

        log.info("==========");
    }
}
