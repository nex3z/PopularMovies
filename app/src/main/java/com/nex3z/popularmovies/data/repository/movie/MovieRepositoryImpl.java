package com.nex3z.popularmovies.data.repository.movie;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;
import com.nex3z.popularmovies.data.entity.movie.MovieEntity;
import com.nex3z.popularmovies.data.local.LocalMovieDataStore;
import com.nex3z.popularmovies.data.local.RealmMovieDataStore;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.MovieService;

import java.util.List;

import io.reactivex.Observable;

public class MovieRepositoryImpl implements MovieRepository {
    private MovieService mMovieService;
    private LocalMovieDataStore mLocalMovieDataStore;

    public MovieRepositoryImpl(RestClient restClient) {
        mMovieService = restClient.getMovieService();
        mLocalMovieDataStore = new RealmMovieDataStore();
    }

    @Override
    public Observable<DiscoveryMovieRespEntity> discoverMovies(int page, String sortBy) {
        return mMovieService.discoverMovies(page, sortBy);
    }

    @Override
    public Observable<Integer> addToFavourite(MovieEntity movieEntity) {
        return mLocalMovieDataStore.addToFavourite(movieEntity);
    }

    @Override
    public Observable<Integer> removeFromFavourite(long movieId) {
        return mLocalMovieDataStore.removeFromFavourite(movieId);
    }

    @Override
    public Observable<List<MovieEntity>> getFavouriteMovies() {
        return mLocalMovieDataStore.getFavouriteMovies();
    }

    @Override
    public Observable<Boolean> isFavourite(long movieId) {
        return mLocalMovieDataStore.isFavourite(movieId);
    }

    @Override
    public Observable<List<Boolean>> isFavourite(List<Long> movieIds) {
        return mLocalMovieDataStore.isFavourite(movieIds);
    }
}
