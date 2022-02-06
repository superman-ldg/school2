package com.ldg.service;

import com.ldg.pojo.User;
import com.ldg.pojo.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */

public interface UserService {
    /***/
    User queryByIdCard(Long id);
    List<User> queryAll();
    int insert(User user);
    int updateUser(User user);
     UserInfo queryUserInfo(Long Id);
     boolean updateUrl(Long id,String url);
     User loadUser(Long id,String password);
}
