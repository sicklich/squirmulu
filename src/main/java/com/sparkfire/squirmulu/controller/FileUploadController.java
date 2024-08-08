package com.sparkfire.squirmulu.controller;

import com.sparkfire.squirmulu.dao.AudioDao;
import com.sparkfire.squirmulu.dao.ImgDao;
import com.sparkfire.squirmulu.entity.AudioFile;
import com.sparkfire.squirmulu.entity.CommonFile;
import com.sparkfire.squirmulu.entity.request.DeleteFileReq;
import com.sparkfire.squirmulu.entity.response.AudioFileSimple;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.service.AudioService;
import com.sparkfire.squirmulu.service.ImgService;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/squ/other/")
public class FileUploadController {

    @Value("${img.path}")
    private String imgPath;

    @Value("${http.img.path}")
    private String imgHttpPath;

    @Value("${audio.path}")
    private String audioPath;

    @Value("${http.audio.path}")
    private String audioHttpPath;

    @Autowired
    private ImgDao imgDao;

    @Autowired
    private ImgService imgService;

    @Autowired
    private AudioDao audioDao;

    @Autowired
    private AudioService audioService;

    @PostMapping("/upload_imgs")
    public CommonResponse<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files, @RequestParam("userID") long userID, @RequestParam("type") int type) {
        // 检查文件是否为空
        if (files.isEmpty()) {
            return CommonResponse.error(HttpStatus.BAD_REQUEST.value(), "File is empty");
        }

        // 指定保存文件的目录
        String uploadDir = imgPath;
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
                imgDao.insert(new CommonFile(randomFilename, userID, now, now, type));
                fileNames.add(imgHttpPath + randomFilename);
            }

            return CommonResponse.success(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to upload file");
        }
    }

    @PostMapping("/img/delete_img")
    public CommonResponse deleteImage(@RequestBody DeleteFileReq req, @RequestAttribute("userId") String userId) {
        // 检查文件名是否为空
        if (req.getFilename() == null || req.getFilename().isEmpty()) {
            return CommonResponse.error(-1, "文件名为空");
        }

        // 指定文件所在的目录
        String uploadDir = imgPath;

        try {
            // 获取文件路径
            Path filePath = Paths.get(uploadDir, req.getFilename());

            // 检查文件是否存在
            if (!Files.exists(filePath)) {
                return CommonResponse.error(-2, "文件不存在");
            }

            // 检查文件是否存在
            if (!(imgDao.getUserIDByFileName(req.getFilename())+"").equals(userId)) {
                return CommonResponse.error(-3, "不能删除非自己的图片");
            }

            // 删除文件
            Files.delete(filePath);

            // 从数据库中删除文件记录
            imgDao.delete(req.getFilename());

            return CommonResponse.success(0);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(-4, "未知错误");
        }
    }

    @PostMapping("/audio/delete_audio")
    public CommonResponse deleteAudio(@RequestBody DeleteFileReq req, @RequestAttribute("userId") String userId) {
        // 检查文件名是否为空
        if (req.getFilename() == null || req.getFilename().isEmpty()) {
            return CommonResponse.error(-1, "文件名为空");
        }

        // 指定文件所在的目录
        String uploadDir = audioPath;

        try {
            // 获取文件路径
            Path filePath = Paths.get(uploadDir, req.getFilename());

            // 检查文件是否存在
            if (!Files.exists(filePath)) {
                return CommonResponse.error(-2, "文件不存在");
            }

            // 检查文件是否存在
            if (!(audioDao.getUserIDByFileName(req.getFilename())+"").equals(userId)) {
                return CommonResponse.error(-3, "不能删除非自己的文件");
            }

            // 删除文件
            Files.delete(filePath);

            // 从数据库中删除文件记录
            audioDao.delete(req.getFilename());

            return CommonResponse.success(0);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(-4, "未知错误");
        }
    }


    @PostMapping("/img/upload_img")
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
            String fileName0 = imgService.uploadImg(file0, imgPath, userID, type, imgHttpPath);
            if (!fileName0.equals("")) fileNames.add(fileName0);
            String fileName1 = imgService.uploadImg(file1, imgPath, userID, type, imgHttpPath);
            if (!fileName1.equals("")) fileNames.add(fileName1);
            String fileName2 = imgService.uploadImg(file2, imgPath, userID, type, imgHttpPath);
            if (!fileName2.equals("")) fileNames.add(fileName2);
            String fileName3 = imgService.uploadImg(file3, imgPath, userID, type, imgHttpPath);
            if (!fileName3.equals("")) fileNames.add(fileName3);
            String fileName4 = imgService.uploadImg(file4, imgPath, userID, type, imgHttpPath);
            if (!fileName4.equals("")) fileNames.add(fileName4);
            String fileName5 = imgService.uploadImg(file5, imgPath, userID, type, imgHttpPath);
            if (!fileName5.equals("")) fileNames.add(fileName5);
            String fileName6 = imgService.uploadImg(file6, imgPath, userID, type, imgHttpPath);
            if (!fileName6.equals("")) fileNames.add(fileName6);
            String fileName7 = imgService.uploadImg(file7, imgPath, userID, type, imgHttpPath);
            if (!fileName7.equals("")) fileNames.add(fileName7);
            String fileName8 = imgService.uploadImg(file8, imgPath, userID, type, imgHttpPath);
            if (!fileName8.equals("")) fileNames.add(fileName8);

            return CommonResponse.success(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to upload file");
        }
    }

    @PostMapping("/audio/upload_audio")
    public CommonResponse<List<AudioFileSimple>> uploadAudio(@RequestParam(value="file0", required = false) MultipartFile file0
            , @RequestParam(value="file1", required = false) MultipartFile file1
            , @RequestParam(value="file2", required = false) MultipartFile file2
            , @RequestParam(value="file3", required = false) MultipartFile file3
            , @RequestParam(value="file4", required = false) MultipartFile file4
            , @RequestParam(value="file5", required = false) MultipartFile file5
            , @RequestParam(value="file6", required = false) MultipartFile file6
            , @RequestParam(value="file7", required = false) MultipartFile file7
            , @RequestParam(value="file8", required = false) MultipartFile file8
            , @RequestParam(value="name0", required = false) String name0
            , @RequestParam(value="name1", required = false) String name1
            , @RequestParam(value="name2", required = false) String name2
            , @RequestParam(value="name3", required = false) String name3
            , @RequestParam(value="name4", required = false) String name4
            , @RequestParam(value="name5", required = false) String name5
            , @RequestParam(value="name6", required = false) String name6
            , @RequestParam(value="name7", required = false) String name7
            , @RequestParam(value="name8", required = false) String name8
            , @RequestParam("userID")

                                                            long userID,
                                                       @RequestParam("type")
                                                            int type) {
        // 检查文件是否为空

        // 指定保存文件的目录
        try {
            // 创建目录
            List<AudioFileSimple> fileNames = new ArrayList<>();

            // 生成随机文件名
            AudioFileSimple fileName0 = audioService.uploadAudio(file0, name0, audioPath, userID, type, audioHttpPath);
            if (fileName0.getFile() != null) fileNames.add(fileName0);
            AudioFileSimple fileName1 = audioService.uploadAudio(file1, name1, audioPath, userID, type, audioHttpPath);
            if (fileName1.getFile() != null) fileNames.add(fileName1);
            AudioFileSimple fileName2 = audioService.uploadAudio(file2, name2, audioPath, userID, type, audioHttpPath);
            if (fileName2.getFile() != null) fileNames.add(fileName2);
            AudioFileSimple fileName3 = audioService.uploadAudio(file3, name3, audioPath, userID, type, audioHttpPath);
            if (fileName3.getFile() != null) fileNames.add(fileName3);
            AudioFileSimple fileName4 = audioService.uploadAudio(file4, name4, audioPath, userID, type, audioHttpPath);
            if (fileName4.getFile() != null) fileNames.add(fileName4);
            AudioFileSimple fileName5 = audioService.uploadAudio(file5, name5, audioPath, userID, type, audioHttpPath);
            if (fileName5.getFile() != null) fileNames.add(fileName5);
            AudioFileSimple fileName6 = audioService.uploadAudio(file6, name6, audioPath, userID, type, audioHttpPath);
            if (fileName6.getFile() != null) fileNames.add(fileName6);
            AudioFileSimple fileName7 = audioService.uploadAudio(file7, name7, audioPath, userID, type, audioHttpPath);
            if (fileName7.getFile() != null) fileNames.add(fileName7);
            AudioFileSimple fileName8 = audioService.uploadAudio(file8, name8, audioPath, userID, type, audioHttpPath);
            if (fileName8.getFile() != null) fileNames.add(fileName8);

            return CommonResponse.success(fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to upload file");
        }
    }
}
