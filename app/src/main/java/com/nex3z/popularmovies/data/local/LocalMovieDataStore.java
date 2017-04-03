package com.nex3z.popularmovies.data.local;

import com.nex3z.popularmovies.data.entity.movie.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

public interface LocalMovieDataStore {

    Observable<Integer> addToFavourite(MovieEntity movieEntity);

    Observable<Integer> removeFromFavourite(long movieId);

    Observable<List<MovieEntity>> getFavouriteMovies();

    Observable<Boolean> isFavourite(long movieId);

}
