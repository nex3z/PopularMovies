package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;

import io.reactivex.Observable;

public interface MovieRepository {

    Observable<DiscoveryMovieRespEntity> discoveryMovies(int page);

}
