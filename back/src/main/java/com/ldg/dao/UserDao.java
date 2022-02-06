package com.ldg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Administrator
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Update("update user set url=#{url} where idCard=#{id}")
    boolean updateUrl(@Param("url")String url, @Param("id")Long id);

    @Select("select * from user where idCard=#{id} and password=#{password}")
    User login(@Param("id") Long id,@Param("password") String password);

    @Select("select * from user where idCard=#{id}")
    User selectOneUser(@Param("id") Long id);

}
