package com.ldg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.Dynamic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface DynamicDao extends BaseMapper<Dynamic> {

    @Update("update dynamic set fabulous=#{fabulous} where id=#{id}")
    void updateFabulous(@Param("id") Long id, @Param("fabulous")Long fabulous);

    @Select("select fabulous from dynamic where id=#{id}")
    int selectFabulous(@Param("id") Long id);



    @Update("update dynamic set url=#{url} where id=#{id}")
    boolean updateUrl(@Param("url") String url,@Param("id") Long id);

}
