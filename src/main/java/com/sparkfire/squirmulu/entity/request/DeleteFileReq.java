package com.sparkfire.squirmulu.entity.request;

public class DeleteFileReq {
    private String filename;

    public DeleteFileReq() {
    }

    public DeleteFileReq(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
