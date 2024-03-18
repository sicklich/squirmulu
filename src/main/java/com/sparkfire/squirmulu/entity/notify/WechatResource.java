package com.sparkfire.squirmulu.entity.notify;

public class WechatResource {
    private String original_type;
    private String algorithm;
    private String ciphertext;
    private String associated_data;
    private String nonce;

    public WechatResource() {
    }

    public WechatResource(String original_type, String algorithm, String ciphertext, String associated_data, String nonce) {
        this.original_type = original_type;
        this.algorithm = algorithm;
        this.ciphertext = ciphertext;
        this.associated_data = associated_data;
        this.nonce = nonce;
    }

    public String getOriginal_type() {
        return original_type;
    }

    public void setOriginal_type(String original_type) {
        this.original_type = original_type;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getAssociated_data() {
        return associated_data;
    }

    public void setAssociated_data(String associated_data) {
        this.associated_data = associated_data;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
