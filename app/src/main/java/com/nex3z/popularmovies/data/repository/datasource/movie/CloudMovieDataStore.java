package com.nex3z.popularmovies.data.repository.datasource.movie;

import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.response.MovieResponse;
import com.nex3z.popularmovies.data.net.service.MovieService;

import java.util.List;

import io.reactivex.Observable;

public class CloudMovieDataStore implements MovieDataStore {
    private RestClient mRestClient;

    public CloudMovieDataStore(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public Observable<List<MovieEntity>> getMovieEntityList(String sortBy) {
        MovieService movieService = mRestClient.getMovieService();
        return movieService.getMovies(sortBy).map(MovieResponse::getMovies);
    }

    @Override
    public Observable<List<MovieEntity>> getMovieEntityList(String sortBy, int page) {
        MovieService movieService = mRestClient.getMovieService();
        return movieService.getMovies(sortBy, page).map(MovieResponse::getMovies);
    }

    @Override
    public Observable<Long> insertMovieEntity(MovieEntity movie) {
        throw new UnsupportedOperationException(
                "The insertMovieEntity method is not supported in cloud data store");
    }

    @Override
    public Observable<Integer> deleteMovieEntity(long movieId) {
        throw new UnsupportedOperationException(
                "The deleteMovieEntity method is not supported in cloud data store");
    }

    @Override
    public Observable<List<Boolean>> checkFavourite(List<Long> movieIds) {
        throw new UnsupportedOperationException(
                "The isFavouriteMovie method is not supported in cloud data store");
    }
}
