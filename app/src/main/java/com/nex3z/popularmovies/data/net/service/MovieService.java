package com.nex3z.popularmovies.data.net.service;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    String SORT_BY_POPULARITY_DESC = "popularity.desc";
    String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";
    String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";
    String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";

    @GET("/3/discover/movie")
    Observable<DiscoveryMovieRespEntity> getMovies(@Query("sort_by") String sortBy,
                                                   @Query("page") int page);

    @GET("/3/discover/movie")
    Observable<DiscoveryMovieRespEntity> getMovies(@Query("sort_by") String sortBy);

}
