package com.nex3z.popularmovies.data.entity;

public abstract class BaseResponse {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
