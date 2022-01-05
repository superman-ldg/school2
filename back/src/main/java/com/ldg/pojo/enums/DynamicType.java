package com.ldg.pojo.enums;

/**
 * @author Administrator
 */

public enum DynamicType {
    /**
     *
     */
    ADVICE("校园通知",1),
    ACTIVITY("活动",2),
    PERSON("个人生活",3),
    Roommate("舍友",4),
    ANTHOR("其它",5);
    String name;
    int type;
    DynamicType(String name,int type){
        this.name=name;
        this.type=type;
    }
}
