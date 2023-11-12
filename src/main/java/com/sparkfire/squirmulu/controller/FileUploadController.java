package com.sparkfire.squirmulu.controller;

import com.sparkfire.squirmulu.dao.ImgDao;
import com.sparkfire.squirmulu.entity.Img;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.service.ImgService;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/squ/other/img/")
public class FileUploadController {

    @Value("${img.path}")
    private String path;

    @Value("${http.path}")
    private String httpPath;

    @Autowired
    private ImgDao imgDao;

    @Autowired
    private ImgService imgService;

    @PostMapping("/upload_imgs")
    public CommonResponse<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files, @RequestParam("userID") long userID, @RequestParam("type") int type) {
        // 检查文件是否为空
        if (files.isEmpty()) {
            return CommonResponse.error(HttpStatus.BAD_REQUEST.value(), "File is empty");
        }

        // 指定保存文件的目录
        String uploadDir = path;
        try {
            // 创建目录
            Files.createDirectories(Paths.get(uploadDir));
            List<String> fileNames = new ArrayList<>();

            // 生成随机文件名
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String randomFilename = SnowflakeGenerator.nextId() + fileExtension;

                // 保存文件到目录
                Path filePath = Paths.get(uploadDir, randomFilename);
                file.transferTo(filePath.toFile());

                //保存到dao
                long now = System.currentTimeMillis() / 1000;
                imgDao.insert(new Img(randomFilename, userID, now, now, type));
                fileNames.add(httpPath + randomFilename);
            }

            return CommonResponse.success(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to upload file");
        }
    }


    @PostMapping("/upload_img")
    public CommonResponse<List<String>> uploadImage(@RequestParam(value="file0", required = false) MultipartFile file0
            , @RequestParam(value="file1", required = false) MultipartFile file1
            , @RequestParam(value="file2", required = false) MultipartFile file2
            , @RequestParam(value="file3", required = false) MultipartFile file3
            , @RequestParam(value="file4", required = false) MultipartFile file4
            , @RequestParam(value="file5", required = false) MultipartFile file5
            , @RequestParam(value="file6", required = false) MultipartFile file6
            , @RequestParam(value="file7", required = false) MultipartFile file7
            , @RequestParam(value="file8", required = false) MultipartFile file8
            , @RequestParam("userID")

                                                    long userID,
                                                    @RequestParam("type")
                                                    int type) {
        // 检查文件是否为空

        // 指定保存文件的目录
        try {
            // 创建目录
            List<String> fileNames = new ArrayList<>();

            // 生成随机文件名
            String fileName0 = imgService.uploadImg(file0, path, userID, type, httpPath);
            if (!fileName0.equals("")) fileNames.add(fileName0);
            String fileName1 = imgService.uploadImg(file1, path, userID, type, httpPath);
            if (!fileName1.equals("")) fileNames.add(fileName1);
            String fileName2 = imgService.uploadImg(file2, path, userID, type, httpPath);
            if (!fileName2.equals("")) fileNames.add(fileName2);
            String fileName3 = imgService.uploadImg(file3, path, userID, type, httpPath);
            if (!fileName3.equals("")) fileNames.add(fileName3);
            String fileName4 = imgService.uploadImg(file4, path, userID, type, httpPath);
            if (!fileName4.equals("")) fileNames.add(fileName4);
            String fileName5 = imgService.uploadImg(file5, path, userID, type, httpPath);
            if (!fileName5.equals("")) fileNames.add(fileName5);
            String fileName6 = imgService.uploadImg(file6, path, userID, type, httpPath);
            if (!fileName6.equals("")) fileNames.add(fileName6);
            String fileName7 = imgService.uploadImg(file7, path, userID, type, httpPath);
            if (!fileName7.equals("")) fileNames.add(fileName7);
            String fileName8 = imgService.uploadImg(file8, path, userID, type, httpPath);
            if (!fileName8.equals("")) fileNames.add(fileName8);u

            return CommonResponse.success(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to upload file");
        }
    }
}
