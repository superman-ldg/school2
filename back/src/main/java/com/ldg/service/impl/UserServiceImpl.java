package com.ldg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldg.dao.UserDao;
import com.ldg.pojo.Dynamic;
import com.ldg.pojo.Goods;
import com.ldg.pojo.User;
import com.ldg.pojo.UserInfo;
import com.ldg.service.UserService;
import com.ldg.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private DynamicServiceImpl dynamicService;

    private GoodsServiceImpl goodsService;

    private MessageUtil messageUtil;

    @Autowired
    UserServiceImpl(UserDao userDao, DynamicServiceImpl dynamicService, GoodsServiceImpl goodsService, MessageUtil messageUtil){
        this.userDao=userDao;
        this.dynamicService=dynamicService;
        this.goodsService=goodsService;
        this.messageUtil=messageUtil;
    }

    @Override
    public User queryByIdCard(Long id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id_card",id);
        return userDao.selectOne(wrapper);
    }

    @Override
    public boolean updateUrl(Long id,String url) {
        return userDao.updateUrl(url,id);
    }

    @Override
    public List<User> queryAll() {
        return userDao.selectList(null);
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User queryUserById_CardNndPassword(int id_card,String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id_card",id_card).eq("password",password);
        return userDao.selectOne(wrapper);
    }

    @Override
    public int updateUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",user.getId());

        return userDao.update(user,wrapper);
    }

    /**
     * 开启异步查询
     * 获取用户的信息包括物品和动态
     * @param uid
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfo queryUserInfo(Long uid) {
        try {
            CompletableFuture<List<Dynamic>> future1 = CompletableFuture.supplyAsync(() -> dynamicService.queryUserDynamic(uid));
            CompletableFuture<List<Goods>> future2 = CompletableFuture.supplyAsync(() -> goodsService.queryUserGoods(uid));
            CompletableFuture<User> future3 = CompletableFuture.supplyAsync(() -> userDao.selectById(uid));
            UserInfo<Dynamic, Goods> userInfo = new UserInfo<>();
            List<Dynamic> dynamics = future1.get();
            List<Goods> goods1 = future2.get();
            User user = future3.get();
            user.setEmail("").setPhone("").setPassword("").setId_card(0L);
            userInfo.setUser(user);
            userInfo.setDynamicData(dynamics);
            userInfo.setGoodData(goods1);
            return userInfo;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询用户信息出错");
        }
    }


}
