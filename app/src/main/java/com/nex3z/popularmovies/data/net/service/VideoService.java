package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.entity.video.VideoRespEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VideoService {

    @GET("/3/movie/{movieId}/videos")
    Observable<VideoRespEntity> getVideos(@Path("movieId") long movieId);

}
