package com.ldg.config.response;

import com.ldg.config.exception.SchoolException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class ResponseModel<T> implements Serializable {
    /**数据条数*/
    int count;
    int pages;
    /**数据*/
    List<T> data;
    /**响应码*/
    int code;
    /**响应信息*/
    String message;
    public ResponseModel(){}
    public ResponseModel(SchoolException error){
        this.code=error.getCode();
        this.message=error.getMessage();
    }

    /**
     * 请求出错或请求出现异常的返回
     * @param error
     * @return
     */
    public static ResponseModel error(SchoolException error){
        ResponseModel<Object> response = new ResponseModel<>();
        response.setCode(error.getCode());
        response.setMessage(error.getMessage());
        return response;
    }
    /**
     * 请求成功时的时间返回
     * @param data
     * @return
     */
    public ResponseModel<T> success(List<T> data){
        ResponseModel<T> response = new ResponseModel<>();
        response.setCode(200);
        response.setMessage("请求成功!");
        response.setData(data);
        response.setCount(data.size());
        return response;
    }

}
