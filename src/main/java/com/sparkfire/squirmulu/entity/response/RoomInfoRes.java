package com.sparkfire.squirmulu.entity.response;

import java.util.List;

public class RoomInfoRes {

    private long id;
    private String auth_role;
    List<GameInfoElement> infoElements;

    public RoomInfoRes(long id, String auth_role, List<GameInfoElement> infoElements) {
        this.id = id;
        this.auth_role = auth_role;
        this.infoElements = infoElements;
    }

    public String getAuth_role() {
        return auth_role;
    }

    public void setAuth_role(String auth_role) {
        this.auth_role = auth_role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GameInfoElement> getInfoElements() {
        return infoElements;
    }

    public void setInfoElements(List<GameInfoElement> infoElements) {
        this.infoElements = infoElements;
    }
}
