package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/3/discover/movie")
    Observable<DiscoveryMovieRespEntity> discoverMovies(@Query("page") int page,
                                                        @Query("sort_by") String sortBy);

    @GET("/3/discover/movie")
    Observable<DiscoveryMovieRespEntity> discoverMovies(@Query("sort_by") String sortBy);

}
