package com.nex3z.popularmovies.data.net.response;


import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.entity.ReviewEntity;

import java.util.List;

public class ReviewResponse {
    @SerializedName("id")
    private long movieId;

    private long page;

    @SerializedName("results")
    private List<ReviewEntity> reviews;

    public List<ReviewEntity> getReviews() {
        return reviews;
    }
}
