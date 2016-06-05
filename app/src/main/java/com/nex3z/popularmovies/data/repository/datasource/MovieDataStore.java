package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.data.entity.MovieEntity;

import java.util.List;

import rx.Observable;

public interface MovieDataStore {

    Observable<List<MovieEntity>> movieEntityList(String sortBy);

    Observable<List<MovieEntity>> movieEntityList(String sortBy, int page);

}
