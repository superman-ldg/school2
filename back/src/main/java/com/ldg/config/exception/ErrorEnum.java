package com.ldg.config.exception;

/**
 * @author Administrator
 */

public enum ErrorEnum implements BaseErrorInfo{
    /**
     *常用错误类型
     */
    SUCCESS(200,"请求成功"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!")
    ;
    int code;
    String message;

    ErrorEnum(int code,String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public int Code() {
        return code;
    }

    @Override
    public String Message() {
        return message;
    }
}
