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

    String type;
    HttpServletResponse response;

    public EventMq(String type, Object source, HttpServletResponse response) {
        super(source);
        this.type=type;
        this.response=response;
    }
    public  HttpServletResponse getResponse(){
        return response;
    }

    public String getType(){
        return type;
    }


}
