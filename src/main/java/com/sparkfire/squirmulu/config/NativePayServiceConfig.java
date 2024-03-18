package com.sparkfire.squirmulu.config;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.partnerpayments.nativepay.NativePayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class NativePayServiceConfig {

    @Value("${merchant.id}")
    private String merchantId;

    @Value("${private.key.path}")
    private String privateKeyPath;

    @Value("${merchant.serial.number}")
    private String merchantSerialNumber;

    @Value("${api.v3.key}")
    private String apiV3Key;

    @Bean
    public NativePayService nativePayService() {
        RSAAutoCertificateConfig config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();

        return new NativePayService.Builder().config(config).build();
    }
}
