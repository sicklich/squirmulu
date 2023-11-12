package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.dao.ImgDao;
import com.sparkfire.squirmulu.entity.Img;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImgService {
    @Autowired
    ImgDao imgDao;

    public String uploadImg(MultipartFile file, String path, long userID, int type, String httpPath) throws IOException {
        if (null == file || file.isEmpty()) {
            return "";
        }
        // 创建目录
        Files.createDirectories(Paths.get(path));

        // 生成随机文件名

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomFilename = SnowflakeGenerator.nextId() + fileExtension;

        // 保存文件到目录
        Path filePath = Paths.get(path, randomFilename);
        file.transferTo(filePath.toFile());

        //保存到dao
        long now = System.currentTimeMillis() / 1000;
        imgDao.insert(new Img(randomFilename, userID, now, now, type));
        return httpPath + randomFilename;
    }
}
