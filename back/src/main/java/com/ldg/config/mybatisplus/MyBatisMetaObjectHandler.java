package com.ldg.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ldg.service.impl.utils.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Administrator
 */
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private DateUtil dateUtil;

    @Override
    public void insertFill(MetaObject metaObject) {
        String date=DateUtil.format(new Date());
      this.setFieldValByName("createtime",date,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
