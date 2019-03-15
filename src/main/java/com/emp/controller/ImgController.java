package com.emp.controller;

import com.emp.enums.HttpResultEnum;
import com.emp.pojo.result.ApiResult;
import com.emp.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 头像接口api
 * @Author: Joker
 * @Date: 2019/2/15 14:16
 */
@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    /**
     * 上传头像
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ApiResult upload(@RequestParam(value = "file") MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
            return new ApiResult(HttpResultEnum.MIS_PARAM);
        }
        try {
            String imgUrl = imgService.save(multipartFile);
            if(imgUrl == null){
                return new ApiResult(HttpResultEnum.EXCEPTION);
            }
            return new ApiResult(imgUrl);
        }catch (IOException e){
            return new ApiResult(HttpResultEnum.EXCEPTION);
        }
    }
}
