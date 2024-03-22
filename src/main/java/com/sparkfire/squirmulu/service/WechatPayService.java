package com.sparkfire.squirmulu.service;


import ch.qos.logback.core.net.ObjectWriter;
import com.sparkfire.squirmulu.entity.notify.WechatResource;
import com.sparkfire.squirmulu.entity.request.PrePayRequest;
import com.sparkfire.squirmulu.entity.response.PrePayResponse;
import com.sparkfire.squirmulu.util.AesUtil;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class WechatPayService {
//    @Autowired
//    NativePayService nativePayService;

    @Value("${merchant.id}")
    private String merchantId;

    @Value("${app.id}")
    private String appID;

    @Value("${merchant.serial.number}")
    private String merchantSerialNumber;

    @Value("${private.key.path}")
    private String privateKeyPath;

    @Value("${api.v3.key}")
    private String apiV3Key;

    @Value("${notify.url}")
    private String notifyUrl;

    @Autowired
    ObjectWriter objectWriter;

    public PrePayResponse prePay(PrePayRequest prepayRequest){
        RSAAutoCertificateConfig config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        NativePayService payService = new NativePayService.Builder().config(config).build();
        PrepayRequest request = new PrepayRequest();
//        Amount amount = new Amount();
//        amount.setTotal(100);
//        amount.setCurrency();
        request.setAppid(appID);
        request.setMchid(merchantId);
        request.setDescription(prepayRequest.getDescription());
        request.setNotifyUrl(notifyUrl);
        request.setOutTradeNo(SnowflakeGenerator.nextId()+"");
        request.setAmount(prepayRequest.getAmount());
        // 调用下单方法，得到应答
        PrepayResponse response = payService.prepay(request);
        System.out.println("code_url:"+response.getCodeUrl());
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        return new PrePayResponse(response.getCodeUrl());
    }


    public void notifyProcess(WechatResource resource) throws GeneralSecurityException, IOException {
        String res = AesUtil.decryptToString(apiV3Key.getBytes(), resource.getAssociated_data().getBytes()
                , resource.getNonce().getBytes(), resource.getCiphertext());
        System.out.println(res);



    }
}
