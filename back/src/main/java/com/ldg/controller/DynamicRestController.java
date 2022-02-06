package com.ldg.controller;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.ldg.config.exception.SchoolException;
import com.ldg.config.response.ResponseModel;
import com.ldg.pojo.Dynamic;
import com.ldg.pojo.UserInfo;
import com.ldg.service.impl.DynamicServiceImpl;
import com.ldg.service.impl.PictureService;
import com.ldg.service.impl.UserServiceImpl;
import com.ldg.service.impl.utils.DynamicRedis;
import com.ldg.service.impl.utils.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags="动态接口")
@RestController
@RequestMapping("/dynamic")
public class DynamicRestController {

    /**
     * 获取日志对象，构造函数传入当前类，查找日志方便定位
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DynamicServiceImpl dynamicService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DynamicRedis dynamicRedis;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private Sequence sequence;

    @ApiOperation(value = "开始查询所有动态，默认返回最新的100条")
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

    @ApiOperation(value = "查询动态的下一页功能，默认返回最新的100条")
    @GetMapping(value = "query/page/{pageId}")
    public ResponseModel queryNext(@PathVariable("pageId") int pageId){
        List<Dynamic> dynamics = dynamicService.queryDynamicPage(pageId);
        ResponseModel<Dynamic> res = new ResponseModel<>();
        if(!dynamics.isEmpty()){
            return new ResponseModel<Dynamic>().success(dynamics).setCount(dynamics.size());
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "查询某个用户的所有动态")
    @PostMapping(value = "query/{uid}")
    public UserInfo queryByUser(@PathVariable Long uid){
       return userService.queryUserInfo(uid);
    }


    @ApiOperation(value = "按动态名称查询")
    @PostMapping("query/title")
    public ResponseModel queryDynamicByTitle(@RequestBody String title){
        List<Dynamic> dynamics = dynamicService.queryDynamicByTitle(title);
        ResponseModel<Dynamic> res = new ResponseModel<>();
        if(!dynamics.isEmpty()){
            return new ResponseModel<Dynamic>().success(dynamics).setCount(dynamics.size());
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "按发布日期查询")
    @GetMapping("query/{date}")
    public ResponseModel queryDynamicByDate(@PathVariable String date){
        List<Dynamic> dynamics = dynamicService.queryDynamicByDate(null);
        ResponseModel<Dynamic> res = new ResponseModel<>();
        if(!dynamics.isEmpty()){
            return new ResponseModel<Dynamic>().success(dynamics).setCount(dynamics.size());
        }else{
            return ResponseModel.error(new SchoolException(404,"获取数据失败！"));
        }
    }

    @ApiOperation(value = "点赞动态操作")
    @GetMapping("fabulous/{id}")
    public void fabulous(@PathVariable Long id) {
        dynamicService.updateFabulous(id);
    }


    @ApiOperation(value = "添加动态")
    @PostMapping(value = "add/{file}")
    public String addDynamic(@RequestBody Dynamic dynamic,@PathVariable("file") String file){
        Long id=sequence.nextId();
        dynamic.setId(id);
        String url = PictureService.BASEURL +pictureService.getFileName(file, "dynamic", id);
        dynamic.setUrl(url);
        boolean b = messageUtil.send(dynamic);
        return b?String.valueOf(id):"null";
    }


    @ApiOperation(value = "按id删除动态动态")
    @GetMapping(value = "delete/{id}")
    public boolean deleteDynamic(@PathVariable("id") Long id){
        boolean b = dynamicService.deleteDynamic(id);
        return b;
    }
    @ApiOperation(value = "按id获取动态的点赞数")
    @GetMapping("get/zan/{id}")
    public int getZanNumber(@PathVariable("id") Long id){
        return dynamicRedis.getOneDynamicZan(id);
    }

    /**
     *  上传图片
     */
    @ApiOperation(value = "上传图片")
    @PutMapping("picture/{id}")
    public boolean updatePicture(MultipartFile file, @PathVariable("id") String sid) throws IOException {
        if(!sid.equals("null")) {
            Long id = Long.parseLong(sid);
            System.out.println("动态图片");
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            assert originalFilename != null;
            String url = pictureService.getFileName(originalFilename, "dynamic", id);
            String url2 = PictureService.BASEURL + url;
            boolean b=dynamicService.uploadPicture(url2, id);
            return pictureService.uploadFile(inputStream, url);
        }else {
            return false;
        }
    }

    @ApiOperation(value = "根据动态删除图片")
    @GetMapping("picture/delete/{name}")
    public boolean deletePicture(@PathVariable("name") String name){
        return  pictureService.deleteFile(name);
    }

}
