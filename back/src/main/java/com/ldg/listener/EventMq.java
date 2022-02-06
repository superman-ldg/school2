package com.ldg.listener;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class EventMq extends ApplicationEvent {

    String msgId;

    public EventMq(String msgId,Object source) {
        super(source);
        this.msgId=msgId;
    }

    public String getMsgId(){
        return msgId;
    }


}
