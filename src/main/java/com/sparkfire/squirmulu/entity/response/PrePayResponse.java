package com.sparkfire.squirmulu.entity.response;

public class PrePayResponse {
    String code_url;

    public PrePayResponse() {
    }

    public PrePayResponse(String code_url) {
        this.code_url = code_url;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
