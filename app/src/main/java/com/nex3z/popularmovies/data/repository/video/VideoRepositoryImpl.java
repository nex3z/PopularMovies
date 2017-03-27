package com.nex3z.popularmovies.data.repository.video;

import com.nex3z.popularmovies.data.entity.video.VideoRespEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.VideoService;

import io.reactivex.Observable;

public class VideoRepositoryImpl implements VideoRepository {

    private VideoService mVideoService;

    public VideoRepositoryImpl(RestClient restClient) {
        mVideoService = restClient.getVideoService();
    }

    @Override
    public Observable<VideoRespEntity> getVideos(long movieId) {
        return mVideoService.getVideos(movieId);
    }

}
