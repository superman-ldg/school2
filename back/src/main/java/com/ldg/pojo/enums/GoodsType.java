package com.ldg.pojo.enums;

/**
 * @author Administrator
 */

public enum GoodsType {
    /**
     *
     */
    ID_CARD("ID卡",1),
    BOOK("文具",2),
    E_Product("电子产品",3),
    MONEY("钱财",4),
    ANTHER("其它",5);
    String name;
    int type;
    GoodsType(String name,int type){
        this.name=name;
        this.type=type;
    }
}
