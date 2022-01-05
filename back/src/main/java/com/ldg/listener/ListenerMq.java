package com.ldg.listener;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Component
public class ListenerMq implements ApplicationListener<EventMq> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(EventMq eventMq) {
        String type=eventMq.getType();
        HttpServletResponse response=eventMq.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        if("成功".equals(type)){
            response.setStatus(200);
            response.getWriter().write("发送成功，正在等待审核！");
        }else{
            response.setStatus(500);
            response.getWriter().write("服务器内部异常,请稍后重试！");
        }

    }
}
