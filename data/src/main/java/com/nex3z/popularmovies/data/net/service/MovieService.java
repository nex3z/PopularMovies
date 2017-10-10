package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieResponse;
import com.nex3z.popularmovies.data.entity.review.GetMovieReviewsResponse;
import com.nex3z.popularmovies.data.entity.video.GetMovieVideosResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MovieService {

    @GET("/3/discover/movie")
    Single<DiscoverMovieResponse> discoverMovies(@QueryMap Map<String, String> params);

    @GET("/3/movie/{movieId}/reviews")
    Single<GetMovieReviewsResponse> getReviews(@Path("movieId") long movieId);

    @GET("/3/movie/{movieId}/videos")
    Single<GetMovieVideosResponse> getVideos(@Path("movieId") long movieId);

}
