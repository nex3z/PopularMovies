package com.nex3z.popularmovies.domain.repository;

import com.nex3z.popularmovies.domain.Movie;

import java.util.List;

import rx.Observable;

public interface MovieRepository {
    Observable<List<Movie>> movies(String sortBy);
    Observable<List<Movie>> movies(String sortBy, int page);
    Observable<Long> insertMovie(Movie movie);
    Observable<Integer> deleteMovie(long movieId);
}
