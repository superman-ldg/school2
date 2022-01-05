package com.ldg.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class PublishMq implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }

    public void publishEvent(EventMq event){
        this.publisher.publishEvent(event);
    }

}
