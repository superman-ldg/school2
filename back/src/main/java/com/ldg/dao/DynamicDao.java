package com.ldg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.Dynamic;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface DynamicDao extends BaseMapper<Dynamic> {

    @Update("update from dynamic set fabulous=#{fabulous} where id=#{id}")
    public void updateFabulous(Long id,Long fabulous);

    @Update("update from dynamic set url=#{url} where id=#{id}")
    boolean updateUrl(String url,Long id);

}
