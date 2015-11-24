package com.nex3z.popularmovies.data.rest.model;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.model.Review;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ReviewResponse {
    
    @SerializedName("id")
    long movieId;

    long page;

    @SerializedName("results")
    List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

}