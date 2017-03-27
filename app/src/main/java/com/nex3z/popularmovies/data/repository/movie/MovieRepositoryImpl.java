package com.nex3z.popularmovies.data.repository.movie;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.MovieService;

import io.reactivex.Observable;

public class MovieRepositoryImpl implements MovieRepository {

    private MovieService mMovieService;

    public MovieRepositoryImpl(RestClient restClient) {
        mMovieService = restClient.getMovieService();
    }

    @Override
    public Observable<DiscoveryMovieRespEntity> discoverMovies(String sortBy, int page) {
        return mMovieService.discoverMovies(sortBy, page);
    }
}
