package com.emp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:13
 */
public interface ImgService {

    /**
     * 保存头像
     * @param multipartFile
     * @return 文件url
     */
    String save(MultipartFile multipartFile) throws IOException;
}
