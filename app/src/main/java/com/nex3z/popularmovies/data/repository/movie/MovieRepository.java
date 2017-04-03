package com.nex3z.popularmovies.data.repository.movie;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;
import com.nex3z.popularmovies.data.entity.movie.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

public interface MovieRepository {

    Observable<DiscoveryMovieRespEntity> discoverMovies(int page, String sortBy);

    Observable<Integer> addToFavourite(MovieEntity movieEntity);

    Observable<Integer> removeFromFavourite(long movieId);

    Observable<List<MovieEntity>> getFavouriteMovies();

    Observable<Boolean> isFavourite(long movieId);

}
