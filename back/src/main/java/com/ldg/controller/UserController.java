package com.ldg.controller;

import com.ldg.config.response.ResponseModel;
import com.ldg.pojo.User;
import com.ldg.service.impl.PictureService;
import com.ldg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PictureService pictureService;

    @PostMapping("add")
    public boolean allUser(@RequestBody User user){
        int insert = userService.insert(user);
        return insert>0;
    }

    @PostMapping("login")
    public User login(@RequestBody User userInfo){
        User user = userService.loadUser(userInfo.getIdCard(), userInfo.getPassword());
        return user;
    }
    @GetMapping("all")
    public List<User> get(){
        return userService.queryAll();
    }


    /**
     *  要修改
     */
    @PostMapping("/update/picture/{id}")
    public boolean updatePicture(MultipartFile file,@PathVariable("id") Long id) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        assert originalFilename != null;
        String url = pictureService.getFileName(originalFilename,"user",id);
        String url2=PictureService.BASEURL+url;
        userService.updateUrl(id,url2);
        return pictureService.uploadFile(inputStream, url);
    }
}
