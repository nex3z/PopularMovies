package com.nex3z.popularmovies.data.repository.datasource.video;

import com.nex3z.popularmovies.data.entity.VideoEntity;

import java.util.List;

import io.reactivex.Observable;

public interface VideoDataStore {

    Observable<List<VideoEntity>> videoEntityList(long movieId);

}
