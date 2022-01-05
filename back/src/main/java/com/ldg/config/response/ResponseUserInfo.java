package com.ldg.config.response;

import com.ldg.pojo.Dynamic;
import com.ldg.pojo.Goods;
import com.ldg.pojo.User;
import com.ldg.pojo.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 获取用户的动态和物品信息的返回消息体
 */
@Data
public class ResponseUserInfo<D,G> implements Serializable {
    int code;
    String message;
    private User user;
    private List<D> dynamicList;
    private List<G> goodsList;

    ResponseUserInfo(UserInfo<D,G> info){
        this.dynamicList=info.getDynamicData();
        this.goodsList=info.getGoodData();
    }


}
