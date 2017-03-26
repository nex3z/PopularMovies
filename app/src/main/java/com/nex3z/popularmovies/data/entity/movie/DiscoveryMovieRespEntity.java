package com.nex3z.popularmovies.data.entity.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoveryMovieRespEntity {

    @SerializedName("page") private int mPage;
    @SerializedName("results") private List<MovieEntity> mResults = null;
    @SerializedName("total_results") private int mTotalResults;
    @SerializedName("total_pages") private int mTotalPages;

    @Override
    public String toString() {
        return "DiscoveryMovieRespEntity{" +
                "mPage=" + mPage +
                ", mResults=" + mResults +
                ", mTotalResults=" + mTotalResults +
                ", mTotalPages=" + mTotalPages +
                '}';
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public List<MovieEntity> getResults() {
        return mResults;
    }

    public void setResults(List<MovieEntity> results) {
        mResults = results;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }
}
