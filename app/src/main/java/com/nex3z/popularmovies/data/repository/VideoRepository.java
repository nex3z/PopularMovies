package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.video.VideoRespEntity;

import io.reactivex.Observable;

public interface VideoRepository {

    Observable<VideoRespEntity> getVideos(long movieId);

}