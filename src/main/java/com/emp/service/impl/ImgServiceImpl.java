package com.emp.service.impl;

import com.emp.service.ImgService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/15 14:14
 */
@Service
public class ImgServiceImpl implements ImgService {

    @Override
    public String save(MultipartFile multipartFile) throws IOException {
        // 1.判断是否为图片 abc.jpg
        String fileName = multipartFile.getOriginalFilename();
        // 使用正则表达式进行判断
        if (!fileName.matches("^.*(jpg|png|gif)$")) { // . 代表任意的字符 * 代表有任意多个的
            // 表示不是图片
            return null;
        }
        try {
            // 判断是否为恶意程序 uploadFile.getInputStream() 文件流
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            // 获取图片宽度和高度 (图片宽高为 0 ,则图片是假的图片)
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0) {
                return null;
            }
            // 准备文件存储的路径()
            String localDir = "F:/workspace/wx/img"; // 本地文件夹
            // 生成文件夹
            File picFile = new File(localDir); // 文件夹的文件执行
            if (!picFile.exists()) { // 文件夹不存在 就创建文件夹
                picFile.mkdirs();
            }
            int index = fileName.lastIndexOf(".");
            String extension = "";
            if (index > 0) {
                extension = fileName.substring(index);
            }
            String newFilename = DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + extension;
            // 生成文件的本地磁盘的路径
            String localPath = localDir + "/" + newFilename;
            return localPath;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
