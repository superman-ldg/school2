package com.ldg.config.exception;


import com.ldg.config.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SchoolException.class)
    @ResponseBody
    public ResponseModel errorSchool(HttpServletRequest request,SchoolException error){
        return ResponseModel.error(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public void exceptionHandel(NullPointerException e){
        logger.error("发送空指针异常,原因是:",e);
        e.printStackTrace();
    }
    @ExceptionHandler(Exception.class)
    public void exceptionHandel(Exception e){
        logger.error("未知异常,原因是:",e);
        e.printStackTrace();
    }



}
