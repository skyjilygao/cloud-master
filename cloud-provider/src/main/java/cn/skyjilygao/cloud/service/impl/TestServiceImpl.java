package cn.skyjilygao.cloud.service.impl;

import cn.skyjilygao.cloud.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Slf4j
@Component
@DubboService(group="DEFAULT_GROUP")
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String name) {
        String format = String.format("Hello %s, time:%s", name, ZonedDateTime.now());
        log.info(format);
        return format;
    }
}
