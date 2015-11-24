package com.nex3z.popularmovies.data.rest.service;

import com.nex3z.popularmovies.data.rest.model.ReviewResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ReviewService {
    @GET("/3/movie/{movieId}/reviews")
    Observable<ReviewResponse> getReviews(@Path("movieId") long movieId);
}
