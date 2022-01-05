package com.ldg.controller;

import com.ldg.config.exception.SchoolException;
import com.ldg.config.response.ResponseModel;
import com.ldg.pojo.Goods;
import com.ldg.pojo.UserInfo;
import com.ldg.service.impl.GoodsServiceImpl;
import com.ldg.service.impl.UserServiceImpl;
import com.ldg.utils.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags="物品接口")
@RestController
@RequestMapping("/goods")
public class GoodsRestController {

    @Autowired
    private GoodsServiceImpl goodsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MessageUtil messageUtil;

    @ApiOperation(value = "开始查询所有物品，默认返回最新的1000条")
    @GetMapping("query/all")
    public ResponseModel queryAll(){
        List<Goods> Goods = goodsService.queryGoodsAll();
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }
    @ApiOperation(value = "下一页功能，默认返回最新的1000条")
    @GetMapping("query/{pageId}")
    public ResponseModel queryPage(@PathVariable int pageId){
        List<Goods> Goods = goodsService.queryGoodsPage(pageId,20);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "按物品名称查询")
    @PostMapping("query/title")
    public ResponseModel queryGoodByTitle(@RequestBody String title){
        List<Goods> Goods = goodsService.queryGoodsByName(title);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "按发布日期查询")
    @GetMapping("query/{date}")
    public ResponseModel queryGoodByDate(@PathVariable String date){
        List<Goods> Goods = goodsService.queryGoodsByDate(null);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }


    @ApiOperation(value = "添加物品")
    @PostMapping("add")
    public boolean addGood(Goods goods){
        boolean insert = messageUtil.send(goods);
        return insert;
    }

    @ApiOperation(value = "根据物品id删除物品")
    @GetMapping("delete/{id}")
    public boolean deleteGoods(@PathVariable Long id){
        boolean delete = goodsService.delete(id);
        return delete;
    }

    @ApiOperation(value = "查询某个用户发布的所有物品")
    @GetMapping("query/user/{uid}")
    public UserInfo queryUser(@PathVariable Long uid){
        return userService.queryUserInfo(uid);
    }




}
