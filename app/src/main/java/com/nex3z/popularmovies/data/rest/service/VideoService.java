package com.nex3z.popularmovies.data.rest.service;

import com.nex3z.popularmovies.data.rest.model.VideoResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface VideoService {
    @GET("/3/movie/{movieId}/videos")
    Observable<VideoResponse> getVideos(@Path("movieId") long movieId);
}
