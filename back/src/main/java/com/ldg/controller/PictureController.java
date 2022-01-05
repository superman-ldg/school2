package com.ldg.controller;

import com.ldg.service.impl.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 */
@Controller("picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping("upload")
    public Boolean uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        return pictureService.uploadFile(inputStream, originalFilename);
    }

    @GetMapping("delete/{fileName}")
    public Boolean deleteFile(@PathVariable String fileName){
        return false;
    }


}
