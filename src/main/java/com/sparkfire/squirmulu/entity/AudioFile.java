package com.sparkfire.squirmulu.entity;

public class AudioFile {
    private String file;

    private String name;
    private long userID;
    private long c_time;

    private long m_time;

    private int type;

//    public PlayerCard() {
//    }


    public AudioFile(String file, String name, long userID, long c_time, long m_time, int type) {
        this.file = file;
        this.name = name;
        this.userID = userID;
        this.c_time = c_time;
        this.m_time = m_time;
        this.type = type;
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public long getM_time() {
        return m_time;
    }

    public void setM_time(long m_time) {
        this.m_time = m_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
