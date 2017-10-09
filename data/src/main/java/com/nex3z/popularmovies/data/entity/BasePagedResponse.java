package com.nex3z.popularmovies.data.entity;

import com.google.gson.annotations.SerializedName;

public class BasePagedResponse<T> extends BaseListResponse<T> {

    private int page;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalResults")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

}
