package com.nex3z.popularmovies.data.repository.datasource.movie;

import com.nex3z.popularmovies.data.entity.MovieEntity;

import java.util.List;

import rx.Observable;

public interface MovieDataStore {

    Observable<List<MovieEntity>> getMovieEntityList(String sortBy);

    Observable<List<MovieEntity>> getMovieEntityList(String sortBy, int page);

    Observable<Long> insertMovieEntity(MovieEntity movie);

    Observable<Integer> deleteMovieEntity(long movieId);

}