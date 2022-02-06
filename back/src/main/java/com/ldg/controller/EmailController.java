package com.ldg.controller;

import com.ldg.pojo.User;
import com.ldg.service.impl.EmailServiceImpl;
import com.ldg.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "发送邮箱")
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("send/{name}/{email}/{title}/{time}/{uid}")
    public Boolean sendEmail(@PathVariable("name") String name,@PathVariable("email") String email
                            ,@PathVariable("title") String title,@PathVariable("time") String time,@PathVariable("uid") Long uid){

        String CONTEXT="你好同学,我叫"+name+",我在校园捡到了你在"+time+"发布的 '"+title+"' 中的物品,后续归还物品请联系一下我的邮箱"+email+" ";
        User user = userService.queryByIdCard(uid);
        String email1 = user.getEmail();
        emailService.sendSimpleMail(email1,"招领小助手",CONTEXT);
        return true;
    }
}
