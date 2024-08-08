package com.sparkfire.squirmulu.entity.response;

public class AudioFileSimple {
    private String file;

    private String name;

    public AudioFileSimple() {
    }

    public AudioFileSimple(String file, String name) {
        this.file = file;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
