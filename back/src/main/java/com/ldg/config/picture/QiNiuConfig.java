package com.ldg.config.picture;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiNiuConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    private Configuration conf;
    private Auth auth;

    @PostConstruct
    public void init(){
        this.conf=new Configuration(Region.huanan());
        this.auth=Auth.create(accessKey,secretKey);
    }

    /**通过输入流上次*/
    public Boolean uploadFile(InputStream inputStream,String fileName){
        try {
            UploadManager uploadManager = new UploadManager(conf);
            String uploadToken = auth.uploadToken(bucket);
            Response put = uploadManager.put(inputStream, fileName, uploadToken, null, null);
            DefaultPutRet defaultPutRet = JSON.parseObject(put.bodyString(), DefaultPutRet.class);
            return true;
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return false;
    }

    /**字节数组上次*/
    public Boolean uploadFile2(byte[] data,String fileName){
        try {
            UploadManager uploadManager = new UploadManager(conf);
            String uploadToken = auth.uploadToken(bucket);
            Response put = uploadManager.put(data, fileName, uploadToken);
            DefaultPutRet defaultPutRet = JSON.parseObject(put.bodyString(), DefaultPutRet.class);
            return true;
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return false;
    }

    /**删除图片*/
    public Boolean deleteFile(String fileName){
        BucketManager bucketManager = new BucketManager(auth,conf);
        try {
            Response delete = bucketManager.delete(bucket, fileName);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return false;
    }



}
