package com.sparkfire.squirmulu.entity;

import java.math.BigDecimal;

public class PlayerCard {
    private long id;
    private long c_time;
    private long card_creator;
    private String body_info;

    private long m_time;

    public PlayerCard(long id, long c_time, long card_creator, String body_info, long m_time) {
        this.id = id;
        this.c_time = c_time;
        this.card_creator = card_creator;
        this.body_info = body_info;
        this.m_time = m_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public long getCard_creator() {
        return card_creator;
    }

    public void setCard_creator(long card_creator) {
        this.card_creator = card_creator;
    }

    public String getBody_info() {
        return body_info;
    }

    public void setBody_info(String body_info) {
        this.body_info = body_info;
    }

    public long getM_time() {
        return m_time;
    }

    public void setM_time(long m_time) {
        this.m_time = m_time;
    }
}
