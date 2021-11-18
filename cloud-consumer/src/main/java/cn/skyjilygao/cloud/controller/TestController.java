package cn.skyjilygao.cloud.controller;


import cn.skyjilygao.cloud.TestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @DubboReference(group="DEFAULT_GROUP")
    private TestService testService;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "say")
    public String sayHello(String name){
        return testService.sayHello(name);
    }

}
