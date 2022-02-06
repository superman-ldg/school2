package com.ldg.service.scheduledTask;

import com.ldg.service.impl.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

/**
 * 不使用该类
 * @author Administrator
 */
@Configuration
@EnableScheduling
@EnableAsync
public class MQTask {

    @Autowired
    private MessageUtil messageUtil;

    // @Scheduled(cron = "0/5 * * * * ?")
    public void test(){
        System.out.println("-----------------------");
        System.out.println(new Date());
    }
}
