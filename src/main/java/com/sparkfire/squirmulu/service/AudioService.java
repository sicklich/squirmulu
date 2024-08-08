package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.dao.AudioDao;
import com.sparkfire.squirmulu.dao.ImgDao;
import com.sparkfire.squirmulu.entity.AudioFile;
import com.sparkfire.squirmulu.entity.CommonFile;
import com.sparkfire.squirmulu.entity.response.AudioFileSimple;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioService {
    @Autowired
    AudioDao audioDao;

    public AudioFileSimple uploadAudio(MultipartFile file, String name, String path, long userID, int type, String httpPath) throws IOException {
        if (null == file || file.isEmpty()) {
            return new AudioFileSimple();
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
        audioDao.insert(new AudioFile(randomFilename, name, userID, now, now, type));
        return new AudioFileSimple(httpPath + randomFilename, name);
    }
}
