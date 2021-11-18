package cn.skyjilygao.cloud.service.impl;

import cn.skyjilygao.cloud.TestService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@DubboService(group="DEFAULT_GROUP")
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String name) {
        return String.format("Hello %s, time:%s", name, ZonedDateTime.now());
    }
}
