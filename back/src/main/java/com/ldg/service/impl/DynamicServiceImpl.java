package com.ldg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldg.dao.DynamicDao;
import com.ldg.pojo.Dynamic;
import com.ldg.service.DynamicService;
import com.ldg.utils.DynamicRedis;
import com.ldg.utils.MessageUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */

@Service
public class DynamicServiceImpl implements DynamicService {

    private DynamicRedis cacheUtil;

    private RabbitTemplate rabbitTemplate;

    private  DynamicDao dynamicDao;
    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    DynamicServiceImpl(DynamicDao dynamicDao,DynamicRedis redisUtil,RabbitTemplate rabbitTemplate){
        this.dynamicDao=dynamicDao;
        this.rabbitTemplate=rabbitTemplate;
        this.cacheUtil=redisUtil;
    }

    /**
     * 缓存一致性问题，双写一致性问题
     */
    @Override
    public boolean insertDynamic(Dynamic dynamic) {
        cacheUtil.deleteDynamics();
        int insert = dynamicDao.insert(dynamic);
        try {
            Thread.sleep(100);
            cacheUtil.deleteDynamics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return insert > 0;
    }

    /**
     * 缓存一致性问题,延迟双删
     */
    @Override
    public boolean deleteDynamic(Long id) {
        cacheUtil.deleteDynamics();
        int i = dynamicDao.deleteById(id);
        try {
            Thread.sleep(100);
            cacheUtil.deleteDynamics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i>0;
    }

    /**更点赞*/
    @Override
    public void updateFabulous(Long id,Long fabulous) {
        dynamicDao.updateFabulous(id,fabulous);
    }


    @Override
    public List<Dynamic> queryDynamicByTitle(String title) {
        HashMap<String ,Object> map = new HashMap<>(16);
        map.put("title",title);
        return dynamicDao.selectByMap(map);
    }

    @Override
    public List<Dynamic> queryDynamicByType(int type) {
        QueryWrapper<Dynamic> wrapper=new QueryWrapper<>();
        wrapper.eq("type",type);
        return dynamicDao.selectList(wrapper);
    }

    /**缓存获取不到数据则去数据库查*/
    @Override
    public List<Dynamic> queryDynamicAll() {
        List<Dynamic> dynamics = cacheUtil.getDynamics();
        return StringUtils.isEmpty(dynamics)?dynamicDao.selectList(null):dynamics;
    }

    @Override
    public List<Dynamic> queryDynamicByTitleAndType(String name, int type) {
        QueryWrapper<Dynamic> wrapper = new QueryWrapper<>();
        wrapper
                .eq("title",name)
                .eq("type",type);
        return dynamicDao.selectList(wrapper);
    }

    @Override
    public List<Dynamic> queryDynamicByDate(Date date) {
        return null;
    }

    @Override
    public List<Dynamic> queryUserDynamic(Long uId) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("uid",uId);
        return dynamicDao.selectByMap(map);
    }

    @Override
    public List<Dynamic> queryDynamicPage(int pageNum, int size) {
        Page<Dynamic> page = new Page<>(pageNum,size);
        Page<Dynamic> dynamicPage = dynamicDao.selectPage(page, null);
        return dynamicPage.getRecords();
    }

}
