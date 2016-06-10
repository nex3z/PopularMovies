package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.mapper.VideoEntityDataMapper;
import com.nex3z.popularmovies.data.repository.datasource.video.VideoDataStore;
import com.nex3z.popularmovies.data.repository.datasource.video.VideoDataStoreFactory;
import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.domain.repository.VideoRepository;

import java.util.List;

import rx.Observable;

public class VideoDataRepository implements VideoRepository {
    private final VideoDataStoreFactory mVideoDataStoreFactory;
    private final VideoEntityDataMapper mVideoEntityDataMapper;

    public VideoDataRepository(VideoDataStoreFactory factory, VideoEntityDataMapper mapper) {
        mVideoDataStoreFactory = factory;
        mVideoEntityDataMapper = mapper;
    }

    @Override
    public Observable<List<Video>> videos(long movieId) {
        final VideoDataStore videoDataStore = mVideoDataStoreFactory.createCloudVideoDataStore();
        return videoDataStore.videoEntityList(movieId).map(mVideoEntityDataMapper::transform);
    }

}
