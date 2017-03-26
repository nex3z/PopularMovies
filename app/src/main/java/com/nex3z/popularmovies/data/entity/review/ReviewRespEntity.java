package com.nex3z.popularmovies.data.entity.review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewRespEntity {

    @SerializedName("id") private long mId;
    @SerializedName("page") private int page;
    @SerializedName("results") private List<ReviewEntity> mResults = null;
    @SerializedName("total_pages") private int mTotalPages;
    @SerializedName("total_results") private int mTotalResults;

    @Override
    public String toString() {
        return "ReviewRespEntity{" +
                "mId=" + mId +
                ", page=" + page +
                ", mResults=" + mResults +
                ", mTotalPages=" + mTotalPages +
                ", mTotalResults=" + mTotalResults +
                '}';
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ReviewEntity> getResults() {
        return mResults;
    }

    public void setResults(List<ReviewEntity> results) {
        mResults = results;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }
}
