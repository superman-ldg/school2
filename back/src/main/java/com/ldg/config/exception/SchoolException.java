package com.ldg.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Accessors(chain = true)
public class SchoolException extends RuntimeException{
    int code;
    String message;

    public SchoolException(){}

    public SchoolException(String message) {
        super(message);
        this.message=message;
    }
    public SchoolException(int code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }
    public SchoolException(int code,String message,Throwable cases){
        super(message,cases);
        this.code=code;
        this.message=message;
    }
    public SchoolException(ErrorEnum errorEnum){
        super(errorEnum.message);
        this.code=errorEnum.code;
        this.message=errorEnum.message;
    }
    public SchoolException(ErrorEnum errorEnum,Throwable cases){
        super(errorEnum.message,cases);
        this.code=errorEnum.code;
        this.message=errorEnum.message;
    }

}
