package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.data.entity.VideoEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.response.VideoResponse;
import com.nex3z.popularmovies.data.net.service.VideoService;

import java.util.List;

import rx.Observable;

public class CloudVideoDataStore implements VideoDataStore {
    private RestClient mRestClient;

    public CloudVideoDataStore(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public Observable<List<VideoEntity>> videoEntityList(long movieId) {
        VideoService videoService = mRestClient.getVideoService();
        return videoService.getVideos(movieId).map(VideoResponse::getVideos);
    }

}
