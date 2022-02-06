package com.ldg.service.impl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Component
public class JsonUtil {

    private  ObjectMapper objectMapper;

    @Autowired
    JsonUtil(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }

    public String listToString(Object list) throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(list);
        return s;
    }

    public <T> T strToObject(String str,Class<T> cl) throws JsonProcessingException {
        return objectMapper.readValue(str,cl);
    }



}
