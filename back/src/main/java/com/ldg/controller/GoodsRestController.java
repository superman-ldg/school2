package com.ldg.controller;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.ldg.config.exception.SchoolException;
import com.ldg.config.response.ResponseModel;
import com.ldg.pojo.Goods;
import com.ldg.pojo.UserInfo;
import com.ldg.service.impl.GoodsServiceImpl;
import com.ldg.service.impl.PictureService;
import com.ldg.service.impl.UserServiceImpl;
import com.ldg.service.impl.utils.MessageUtil;
import com.ldg.service.impl.utils.UserRedis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    private PictureService pictureService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private Sequence sequence;

    @Autowired
    private  UserRedis userRedis;

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
    @ApiOperation(value = "下一页功能，返回最新的100条")
    @GetMapping("query/page/{pageId}")
    public ResponseModel queryPage(@PathVariable("pageId") int pageId){
        System.out.println("下一页查询："+pageId);
        List<Goods> Goods = goodsService.queryGoodsPage(pageId);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "按物品名称查询")
    @GetMapping("query/search/{title}/{type}")
    public ResponseModel queryGoodByTitle(@PathVariable("title") String title,@PathVariable("type") int type){
        List<Goods> Goods = goodsService.queryGoodsByName(title,type);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "按发布日期查询")
    @GetMapping("query/date/{date}")
    public ResponseModel queryGoodByDate(@PathVariable String date){
        List<Goods> Goods = goodsService.queryGoodsByDate(null);
        if(!Goods.isEmpty()){
            return new ResponseModel<Goods>().success(Goods);
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }


    @ApiOperation(value = "添加物品")
    @PostMapping("add/{file}")
    public String addGood(@RequestBody Goods goods,@PathVariable("file") String file){
        Long id=sequence.nextId();
        goods.setId(id);
        String url = PictureService.BASEURL +pictureService.getFileName(file, "good", id);
        goods.setUrl(url);
        boolean res = messageUtil.send(goods);
        return res?String.valueOf(id):"null";
    }

    @ApiOperation(value = "根据物品id删除物品")
    @GetMapping("delete/{id}/{uid}")
    public boolean deleteGoods(@PathVariable("id") Long id,@PathVariable("uid") Long uid){
        boolean delete = goodsService.delete(id,uid);
        return delete;
    }

    @ApiOperation(value = "查询某个用户发布的所有物品")
    @GetMapping("query/userInfo/{uid}")
    public UserInfo queryUser(@PathVariable("uid") Long uid){
        UserInfo userInfo = userRedis.getUserInfo(uid);
        if(!StringUtils.isEmpty(userInfo)) return userInfo;
        return userService.queryUserInfo(uid);
    }


    /**
     *  上传图片
     */
    @ApiOperation(value = "上传图片")
    @PutMapping("picture/{id}")
    public boolean updatePicture(MultipartFile file, @PathVariable("id") String sid) throws IOException, InterruptedException {
        if(!sid.equals("null")) {
            Long id = Long.parseLong(sid);
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            assert originalFilename != null;
            String url = pictureService.getFileName(originalFilename, "good", id);
            String url2 = PictureService.BASEURL + url;
            boolean b = goodsService.uploadPicture(url2, id);
            return pictureService.uploadFile(inputStream, url);
        }else{
            return false;
        }
    }

    @ApiOperation(value = "根据物品名称删除图片")
    @GetMapping("picture/delete/{name}")
    public boolean deletePicture(@PathVariable("name") String name){
       return  pictureService.deleteFile(name);
    }



}
