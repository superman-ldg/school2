package com.ldg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldg.config.rabbitmq.MyConfirmCallback;
import com.ldg.config.rabbitmq.MyReturnCallback;
import com.ldg.dao.GoodsDao;
import com.ldg.dao.UserDao;
import com.ldg.pojo.Goods;
import com.ldg.service.GoodsService;
import com.ldg.utils.GoodRedis;
import com.ldg.utils.MessageUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private UserDao userDao;

    private GoodsDao goodsDao;

    @Autowired
    private GoodRedis redisUtil;

    @Autowired
    private MyConfirmCallback myConfirmCallback;
    @Autowired
    private MyReturnCallback myReturnCallback;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageUtil messageUtil;



    @Autowired
    GoodsServiceImpl(GoodsDao goodsDao,UserDao userDao){
        this.userDao=userDao;
        this.goodsDao=goodsDao;
    }
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(myConfirmCallback);
        rabbitTemplate.setReturnCallback(myReturnCallback);
    }

    /**
     * 缓存一致性问题，双写一致性问题，读写饥饿问题
     */
    @Override
    public boolean insert(Goods goods) {
//        System.out.println("-----------goodService--------");
//        boolean send = messageUtil.send(goods);
        redisUtil.deleteGoods();
        int insert = goodsDao.insert(goods);
        try {
            Thread.sleep(100);
            redisUtil.deleteGoods();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return insert>0;
        //return send;
    }

    /**
     * 缓存一致性问题
     */
    @Override
    public boolean delete(Long gId) {
        redisUtil.deleteGoods();
        int i = goodsDao.deleteById(gId);
        try {
            Thread.sleep(100);
            redisUtil.deleteGoods();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i>0;
    }

    @Override
    public List<Goods> queryGoodsByName(String name) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return goodsDao.selectList(wrapper);
    }

    @Override
    public List<Goods> queryGoodsByType(int type) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        return goodsDao.selectList(wrapper);
    }

    /**缓存获取不到数据则去数据库查*/
    @Override
    public List<Goods> queryGoodsAll() {
        List<Goods> goods = redisUtil.getGoods();
        return StringUtils.isEmpty(goods)?goodsDao.selectList(null):goods;
    }

    @Override
    public List<Goods> queryGoodsByNameAndType(String name, int type) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name)
                .eq("type",type);
        return goodsDao.selectList(wrapper);
    }

    @Override
    public List<Goods> queryGoodsByDate(Date date) {
        return null;
    }

    @Override
    public List<Goods> queryUserGoods(Long uId) {
        Map<String,Object> map=new HashMap<>(16);
        map.put("uid",uId);
        return goodsDao.selectByMap(map);
    }

    @Override
    public List<Goods> queryGoodsPage(int pageNum, int size) {
        Page<Goods> page = new Page<>(pageNum,size);
        Page<Goods> goodsPage = goodsDao.selectPage(page,null);
        return goodsPage.getRecords();
    }


}
