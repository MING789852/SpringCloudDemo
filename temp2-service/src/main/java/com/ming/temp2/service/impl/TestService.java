package com.ming.temp2.service.impl;

import com.ming.temp2.entity.TempUser;
import com.ming.temp2.mapper.TempUserMapper;
import com.ming.temp2.service.ITestService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService implements ITestService {

    private final TempUserMapper tempUserMapper;


    @Override
    @Transactional
    public void testSeata() {
        String xid = RootContext.getXID();
        log.info("create order in transaction: " + xid);

        TempUser tempUser=tempUserMapper.selectById(1);
        TempUser update=new TempUser();
        update.setId(tempUser.getId());
        update.setPoint(tempUser.getPoint()+1);
        tempUserMapper.updateById(update);
    }
}
