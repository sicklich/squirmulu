package com.sparkfire.squirmulu.entity.response;

import java.util.List;

public class RoomListRes {
    public RoomListRes(List<GameListElement> gameList) {
        this.gameList = gameList;
    }

    List<GameListElement> gameList;

    public List<GameListElement> getGameList() {
        return gameList;
    }

    public void setGameList(List<GameListElement> gameList) {
        this.gameList = gameList;
    }
}
