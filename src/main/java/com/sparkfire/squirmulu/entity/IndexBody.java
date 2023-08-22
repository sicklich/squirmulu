package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexBody {
    private long id;
    private List<IndexTarget> targets;

    public IndexBody(long id, List<IndexTarget> targets) {
        this.id = id;
        this.targets = targets;
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
