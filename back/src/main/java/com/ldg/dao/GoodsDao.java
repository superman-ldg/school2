package com.ldg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.Goods;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Mapper
public interface GoodsDao extends BaseMapper<Goods> {
    @Update("update goods set url=#{url} where id=#{id}")
    boolean updatePictureUrl(@Param("url")String url, @Param("id")Long id);

}
