package com.sparkfire.squirmulu.entity.notify;

import java.util.List;

class PromotionDetail {
    private int amount;

    private int wechatpay_contribute;

    private String couponId;

    private String scope;

    private int merch_int_contribute;

    private String name;

    private int other_contribute;

    private String currency;

    private String stock_id;

    private List<GoodsDetail> goods_detail;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWechatpay_contribute() {
        return wechatpay_contribute;
    }

    public void setWechatpay_contribute(int wechatpay_contribute) {
        this.wechatpay_contribute = wechatpay_contribute;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getMerch_int_contribute() {
        return merch_int_contribute;
    }

    public void setMerch_int_contribute(int merch_int_contribute) {
        this.merch_int_contribute = merch_int_contribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOther_contribute() {
        return other_contribute;
    }

    public void setOther_contribute(int other_contribute) {
        this.other_contribute = other_contribute;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public List<GoodsDetail> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(List<GoodsDetail> goods_detail) {
        this.goods_detail = goods_detail;
    }
}
