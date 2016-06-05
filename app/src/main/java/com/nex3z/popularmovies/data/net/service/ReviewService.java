package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.net.response.ReviewResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ReviewService {
    @GET("/3/movie/{movieId}/reviews")
    Observable<ReviewResponse> getReviews(@Path("movieId") long movieId);
}
