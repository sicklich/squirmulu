package com.sparkfire.squirmulu.entity.notify;

import com.wechat.pay.java.service.payments.nativepay.model.Amount;

import java.util.List;

public class Transaction {

    private String transaction_id;

    private Amount amount;
    private String mchid;

    private String trade_state;

    private String bank_type;

    private List<PromotionDetail> promotion_detail;

    private String success_time;

    private Payer payer;

    private String out_trade_no;

    private String AppId;

    private String trade_state_desc;

    private String trade_type;

    private String attach;

    private SceneInfo scene_info;

    // Getters and setters
    // ...

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public List<PromotionDetail> getPromotion_detail() {
        return promotion_detail;
    }

    public void setPromotion_detail(List<PromotionDetail> promotion_detail) {
        this.promotion_detail = promotion_detail;
    }

    public String getSuccess_time() {
        return success_time;
    }

    public void setSuccess_time(String success_time) {
        this.success_time = success_time;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public SceneInfo getScene_info() {
        return scene_info;
    }

    public void setScene_info(SceneInfo scene_info) {
        this.scene_info = scene_info;
    }
}
