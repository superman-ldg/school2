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
    private DynamicRedis dynamicRedis;

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
    public void updateFabulous(Long id) {

        Long count = dynamicRedis.cacheDynamicZan(id);
        if(count%20==0){
            dynamicDao.updateFabulous(id,count);
        }
    }


    /**按标题模糊查询*/
    @Override
    public List<Dynamic> queryDynamicByTitle(String title) {
        QueryWrapper<Dynamic> wrapper = new QueryWrapper<>();
        wrapper.like("title",title);
        return dynamicDao.selectList(wrapper);
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
        QueryWrapper<Dynamic> wrapper=new QueryWrapper<>();
        wrapper.likeRight("createtime",date);
        return dynamicDao.selectList(wrapper);
    }

    @Override
    public List<Dynamic> queryUserDynamic(Long uId) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("uid",uId);
        return dynamicDao.selectByMap(map);
    }

    /**
     *         wrapper.eq()//等于
     *         wrapper.ne()//不等于
     *         wrapper.gt()//大于
     *         wrapper.ge()//大于等于
     *         wrapper.lt()//小于
     *         wrapper.le()//小于等于
     *         wrapper.between()//在什么之间
     *         wrapper.notBetween()//不在之间
     *         wrapper.select()//选择指定列
     *         wrapper.allEq()//全等于
     *         wrapper.like();wrapper.likeLeft();wrapper.likeRight();
     *         wrapper.isNull();wrapper.isNotNull();wrapper.groupBy();wrapper.having();wrapper.orderBy();
     *         wrapper.or();wrapper.and();wrapper.exists();
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public List<Dynamic> queryDynamicPage(int pageNum, int size) {
        QueryWrapper<Dynamic> wrapper = new QueryWrapper<>();
        wrapper.le("id",100);
        Page<Dynamic> page = new Page<>(pageNum,size);
        Page<Dynamic> dynamicPage = dynamicDao.selectPage(page, null);
        return dynamicPage.getRecords();
    }


    public void updateZanToMysql(){

    }







}
