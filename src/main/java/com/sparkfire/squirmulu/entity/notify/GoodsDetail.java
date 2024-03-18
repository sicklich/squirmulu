package com.sparkfire.squirmulu.entity.notify;

class GoodsDetail {

    private String goods_remark;

    private int quantity;

    private int discount_amount;

    private String goods_id;

    private int unit_price;

    public String getGoods_remark() {
        return goods_remark;
    }

    public void setGoods_remark(String goods_remark) {
        this.goods_remark = goods_remark;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }
}
