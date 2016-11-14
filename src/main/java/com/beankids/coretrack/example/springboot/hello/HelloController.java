package com.beankids.coretrack.example.springboot.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by coretrack on 2016-11-14
 */

@RestController
public class HelloController {

    @Autowired
    private AnyBean anyBean;

    @RequestMapping("/")
    public String hello() {
        return "Hello, Spring Boot";
    }

    @RequestMapping("/profiles")
    public String profiles() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : anyBean.getActiveProfiles()) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    @RequestMapping("/loggingLevel")
    public String loggingLevel() {
        return anyBean.getLoggingLevel();
    }
}
