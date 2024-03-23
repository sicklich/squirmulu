package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.dao.WxTradeDao;
import com.sparkfire.squirmulu.entity.WxTrade;
import com.sparkfire.squirmulu.entity.notify.WechatPayNotify;
import com.sparkfire.squirmulu.entity.notify.WechatResource;
import com.sparkfire.squirmulu.entity.request.PrePayRequest;
import com.sparkfire.squirmulu.entity.response.PrePayResponse;
import com.sparkfire.squirmulu.util.AesUtil;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
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
    ObjectMapper objectMapper;

    @Autowired
    WxTradeDao wxTradeDao;

    public PrePayResponse prePay(PrePayRequest prepayRequest) {
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
        long out_trade_no = SnowflakeGenerator.nextId();
        request.setOutTradeNo(out_trade_no + "");
        request.setAmount(prepayRequest.getAmount());
        // 调用下单方法，得到应答
        PrepayResponse response = payService.prepay(request);
        long now = System.currentTimeMillis() / 1000;
        wxTradeDao.insert(new WxTrade(out_trade_no, 0, prepayRequest.getId(), prepayRequest.getAmount().getTotal()
                , prepayRequest.getDescription(), now, now));
        System.out.println("code_url:" + response.getCodeUrl());
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        return new PrePayResponse(response.getCodeUrl());
    }


    public void notifyProcess(WechatPayNotify notify) throws GeneralSecurityException, IOException {
        // 0创建 1成功 -1失败
        int status = notify.getEvent_type().equals("TRANSACTION.SUCCESS") ? 1 : -1;
        WechatResource resource = notify.getResource();
        String res = AesUtil.decryptToString(apiV3Key.getBytes(), resource.getAssociated_data().getBytes()
                , resource.getNonce().getBytes(), resource.getCiphertext());
        System.out.println(res);
        //解出tradeno
        JsonNode rootNode = objectMapper.readTree(res);
        String outTradeNo = rootNode.get("out_trade_no").asText();
        System.out.println("out_trade_no: " + outTradeNo);

        long outTradeNoAsLong = Long.parseLong(outTradeNo);
        WxTrade trade = new WxTrade();
        trade.setTrade_no(outTradeNoAsLong);
        trade.setStatus(status);
        trade.setEdit_time(System.currentTimeMillis() / 1000);
        wxTradeDao.updateStatus(trade);

    }
}
