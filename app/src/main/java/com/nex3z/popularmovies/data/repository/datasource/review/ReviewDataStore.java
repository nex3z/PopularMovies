package com.nex3z.popularmovies.data.repository.datasource.review;

import com.nex3z.popularmovies.data.entity.ReviewEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ReviewDataStore {
    Observable<List<ReviewEntity>> reviewEntityList(long movieId);
}
