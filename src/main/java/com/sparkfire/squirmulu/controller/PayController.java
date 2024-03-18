package com.sparkfire.squirmulu.controller;

import com.sparkfire.squirmulu.entity.notify.WechatPayNotify;
import com.sparkfire.squirmulu.entity.request.PrePayRequest;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.entity.response.PrePayResponse;
import com.sparkfire.squirmulu.service.WechatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/squ/other/payment/")
public class PayController {
    @Autowired
    WechatPayService wechatPayService;

    @PostMapping("/trigger_native")
    public CommonResponse<PrePayResponse> uploadImages(@RequestBody PrePayRequest request) {
        // 检查文件是否为空
        return CommonResponse.success(wechatPayService.prePay(request));
    }

    @PostMapping("/notify")
    public CommonResponse notify(@RequestBody WechatPayNotify notify) throws GeneralSecurityException, IOException {
        wechatPayService.notifyProcess(notify.getResource());
        return CommonResponse.success("");
    }
}
