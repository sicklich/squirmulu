package com.sparkfire.squirmulu.entity.request;


import com.wechat.pay.java.service.payments.nativepay.model.Amount;

public class PrePayRequest {
    private long id;//用户id
    private String description;
    private Amount amount;

    public PrePayRequest() {
    }

    public PrePayRequest(long id, String description, Amount amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
