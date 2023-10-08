package com.sparkfire.squirmulu.entity;

public class PlayerCardIDString {
    private String id;
    private long card_creator;
    private long c_time;

    private long m_time;

    private String role_card;

//    public PlayerCard() {
//    }


    public PlayerCardIDString(String id, long card_creator, long c_time, long m_time, String role_card) {
        this.id = id;
        this.card_creator = card_creator;
        this.c_time = c_time;
        this.m_time = m_time;
        this.role_card = role_card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCard_creator() {
        return card_creator;
    }

    public void setCard_creator(long card_creator) {
        this.card_creator = card_creator;
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

    public String getRole_card() {
        return role_card;
    }

    public void setRole_card(String role_card) {
        this.role_card = role_card;
    }
}
