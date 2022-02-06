package com.ldg.service;

import com.ldg.pojo.Dynamic;
import com.ldg.pojo.Goods;
import com.ldg.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */

public interface GoodsService {

    boolean insert(Goods goods);
    boolean delete(Long gId,Long uid);
    boolean uploadPicture(String url,Long id);
    List<Goods> queryGoodsByName(String name,int type);
    List<Goods> queryGoodsByType(int type);
    List<Goods> queryGoodsAll();
    List<Goods> queryGoodsByNameAndType(String name,int type);
    List<Goods> queryGoodsByDate(Date date);
    List<Goods> queryUserGoods(Long useId);
    List<Goods> queryGoodsPage(int pageNum);

}
