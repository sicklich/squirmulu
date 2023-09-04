package com.sparkfire.squirmulu.controller;

import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/squ/game")
public class FileUploadController {

    @Value("${img.path}")
    private String path;

    @PostMapping("/upload_img")
    public CommonResponse<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return CommonResponse.error(HttpStatus.BAD_REQUEST.value(),"File is empty");
        }

        // 指定保存文件的目录
        String uploadDir = path;
        try {
            // 创建目录
            Files.createDirectories(Paths.get(uploadDir));

            // 生成随机文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String randomFilename = SnowflakeGenerator.nextId() + fileExtension;

            // 保存文件到目录
            Path filePath = Paths.get(uploadDir, randomFilename);
            file.transferTo(filePath.toFile());

            return CommonResponse.success(randomFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Failed to upload file");
        }
    }
}
