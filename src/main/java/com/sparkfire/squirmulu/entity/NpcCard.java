package com.sparkfire.squirmulu.entity;

import java.math.BigDecimal;

public class NpcCard {
    private long npc_mods;
    private long p_time;
    private long creator;
    private BigDecimal c_price;
    private String body_info;

    private long edit_time;

    public NpcCard(long npc_mods, long p_time, long creator, BigDecimal price, String body_info, long edit_time) {
        this.npc_mods = npc_mods;
        this.p_time = p_time;
        this.creator = creator;
        this.c_price = price;
        this.body_info = body_info;
        this.edit_time = edit_time;
    }

    public long getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(long edit_time) {
        this.edit_time = edit_time;
    }

    public long getNpc_mods() {
        return npc_mods;
    }

    public void setNpc_mods(long npc_mods) {
        this.npc_mods = npc_mods;
    }

    public long getP_time() {
        return p_time;
    }

    public void setP_time(long p_time) {
        this.p_time = p_time;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public BigDecimal getC_price() {
        return c_price;
    }

    public void setC_price(BigDecimal c_price) {
        this.c_price = c_price;
    }

    public String getBody_info() {
        return body_info;
    }

    public void setBody_info(String body_info) {
        this.body_info = body_info;
    }
}
