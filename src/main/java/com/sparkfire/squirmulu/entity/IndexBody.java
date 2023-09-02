package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexBody {
    private long id;

    private long user_id;
    private List<IndexTarget> targets;

    public IndexBody(long id, long user_id, List<IndexTarget> targets) {
        this.id = id;
        this.user_id = user_id;
        this.targets = targets;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<IndexTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<IndexTarget> targets) {
        this.targets = targets;
    }
}
