package com.ldg.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * 日期转化工具类
 * ThreadLocal的使用
 */
@Component
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> DFS =new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    public static Date parse(String str) throws ParseException {
        return DFS.get().parse(str);
    }

    public static String format(Date date){
        return DFS.get().format(date);
    }

    public static void clear(){
        DFS.remove();
    }

}
