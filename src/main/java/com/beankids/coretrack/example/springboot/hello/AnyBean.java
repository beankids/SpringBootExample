package com.beankids.coretrack.example.springboot.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by coretrack on 2016-11-14
 */

@Component
public class AnyBean {

    @Autowired
    private Environment environment;

    @Value("${logging.level.org.springframework:INFO}")
    private String loggingLevel;

    public String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }

    public String getLoggingLevel() {
        return loggingLevel;
    }
}
