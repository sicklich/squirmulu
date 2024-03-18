package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexBodyForAdd {
    private long id;

    private long user_id;
    private List<IndexTargetForAdd> targets;

    public IndexBodyForAdd(long id, long user_id, List<IndexTargetForAdd> targets) {
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

    public List<IndexTargetForAdd> getTargets() {
        return targets;
    }

    public void setTargets(List<IndexTargetForAdd> targets) {
        this.targets = targets;
    }
}
