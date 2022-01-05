package com.ldg.controller;

import com.ldg.config.exception.SchoolException;
import com.ldg.config.response.ResponseModel;
import com.ldg.pojo.Dynamic;
import com.ldg.pojo.UserInfo;
import com.ldg.service.impl.DynamicServiceImpl;
import com.ldg.service.impl.UserServiceImpl;
import com.ldg.utils.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags="动态接口")
@RestController
@RequestMapping("/dynamic")
public class DynamicRestController {

    @Autowired
    private DynamicServiceImpl dynamicService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MessageUtil messageUtil;

    @ApiOperation(value = "开始查询所有动态，默认返回最新的1000条")
    @GetMapping(value = "query/all")
    public ResponseModel query(){
        List<Dynamic> dynamics = dynamicService.queryDynamicAll();
        ResponseModel<Dynamic> res=new ResponseModel<>();
        if(!dynamics.isEmpty()){
            return new ResponseModel<Dynamic>().success(dynamics);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "查询动态的下一页功能，默认返回最新的1000条")
    @GetMapping(value = "query/all/{pageId}")
    public ResponseModel queryNext(@PathVariable int pageId){
        List<Dynamic> dynamics = dynamicService.queryDynamicPage(pageId, 20);
        ResponseModel<Dynamic> res = new ResponseModel<>();
        if(!dynamics.isEmpty()){
            return new ResponseModel<Dynamic>().success(dynamics);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "查询某个用户的所有动态")
    @PostMapping(value = "query/{uid}")
    public UserInfo queryByUser(@PathVariable Long uid){
       return userService.queryUserInfo(uid);
    }

    @ApiOperation(value = "更具动态id点赞动态")
    @GetMapping("fabulous/{id}")
    public void fabulous(@PathVariable Long id){

    }
    @ApiOperation(value = "添加动态")
    @PostMapping(value = "add")
    public boolean addDynamic( Dynamic dynamic){
        boolean b = messageUtil.send(dynamic);
        return b;
    }

    @ApiOperation(value = "按id删除动态动态")
    @DeleteMapping(value = "delete/{id}")
    public boolean deleteDynamic(@PathVariable("id") Long id){
        boolean b = dynamicService.deleteDynamic(id);
        return b;
    }

    @ApiOperation(value = "按id删除动态动态")
    @DeleteMapping(value = "zan/{id}")
    public boolean zanDynamic(@PathVariable("id") Long id){
        boolean b = dynamicService.deleteDynamic(id);
        return b;
    }


}
