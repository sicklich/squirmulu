package com.sparkfire.squirmulu.entity.request;

public class DeleteImgReq {
    private String filename;

    public DeleteImgReq() {
    }

    public DeleteImgReq(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
