package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.net.response.VideoResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface VideoService {
    @GET("/3/movie/{movieId}/videos")
    Observable<VideoResponse> getVideos(@Path("movieId") long movieId);
}
