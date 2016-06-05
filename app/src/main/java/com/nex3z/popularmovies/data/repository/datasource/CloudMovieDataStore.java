package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.response.MovieResponse;
import com.nex3z.popularmovies.data.net.service.MovieService;

import java.util.List;

import rx.Observable;

public class CloudMovieDataStore implements MovieDataStore {
    private RestClient mRestClient;

    public CloudMovieDataStore(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public Observable<List<MovieEntity>> movieEntityList(String sortBy) {
        MovieService movieService = mRestClient.getMovieService();
        return movieService.getMovies(sortBy).map(MovieResponse::getMovies);
    }

    @Override
    public Observable<List<MovieEntity>> movieEntityList(String sortBy, int page) {
        MovieService movieService = mRestClient.getMovieService();
        return movieService.getMovies(sortBy, page).map(MovieResponse::getMovies);
    }

}
