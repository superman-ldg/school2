package com.ldg.service.impl;

import com.ldg.config.picture.QiNiuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author Administrator
 */
@Component
public class PictureService {

    private QiNiuConfig qiNiuConfig;

    @Autowired
    PictureService(QiNiuConfig qiNiuConfig){
        this.qiNiuConfig=qiNiuConfig;
    }

    public Boolean uploadFile(InputStream inputStream,String fileName){
        String realName = getFileName(fileName);
        return  qiNiuConfig.uploadFile(inputStream, realName);
    }

    public boolean deleteFile(String url){
        return qiNiuConfig.deleteFile(url);
    }

    public String getFileName(String file){
        int i = file.lastIndexOf(".");
        String suffix = file.substring(i);
        String name= UUID.randomUUID() +suffix;
       name=name.replace("-","");
       return name;
    }

}
