package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.data.entity.ReviewEntity;

import java.util.List;

import rx.Observable;

public interface ReviewDataStore {
    Observable<List<ReviewEntity>> reviewEntityList(long movieId);
}
