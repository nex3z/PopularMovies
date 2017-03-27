package com.nex3z.popularmovies.data.repository.movie;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;

import io.reactivex.Observable;

public interface MovieRepository {

    Observable<DiscoveryMovieRespEntity> discoverMovies(String sortBy, int page);

}
