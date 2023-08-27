package com.sparkfire.squirmulu.entity.response;

import java.util.List;

public class RoomInfoRes {

    private long id;
    List<GameInfoElement> infoElements;

    public RoomInfoRes(long id, List<GameInfoElement> infoElements) {
        this.id = id;
        this.infoElements = infoElements;
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
