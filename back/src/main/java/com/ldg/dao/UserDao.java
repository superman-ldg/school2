package com.ldg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldg.pojo.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    @Update("update from user set url=#{url} where id=#{id}")
    boolean updateUrl(String url,Long id);
}
