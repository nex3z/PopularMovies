package com.nex3z.popularmovies.data.entity;

import java.util.List;

public class BaseListResponse<T> extends BaseResponse {

    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
