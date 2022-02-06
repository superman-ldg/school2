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
    public static String BASEURL="http://r5yh8ltea.bkt.gdipper.com/";

    @Autowired
    PictureService(QiNiuConfig qiNiuConfig){
        this.qiNiuConfig=qiNiuConfig;
    }

    public Boolean uploadFile(InputStream inputStream,String fileName){
        return  qiNiuConfig.uploadFile(inputStream, fileName);
    }

    public boolean deleteFile(String url){
        return qiNiuConfig.deleteFile(url);
    }

    public String getFileName(String file,String type,Long id){
        int index=file.lastIndexOf('.');
        String suffix=file.substring(index,file.length());
        return type+"::"+id+suffix;
    }

}
