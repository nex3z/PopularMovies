package com.nex3z.popularmovies.data.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieResponse;
import com.nex3z.popularmovies.data.entity.discover.MovieEntity;
import com.nex3z.popularmovies.data.entity.review.GetMovieReviewsResponse;
import com.nex3z.popularmovies.data.entity.video.GetMovieVideosResponse;
import com.nex3z.popularmovies.data.local.MovieDatabase;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.MovieService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String LOG_TAG = MovieRepositoryImpl.class.getSimpleName();
    private MovieService mMovieService;
    private MovieDatabase mMovieDatabase;

    public MovieRepositoryImpl(Context context, RestClient restClient) {
        mMovieService = restClient.getMovieService();
        mMovieDatabase = Room.databaseBuilder(context.getApplicationContext(),
                MovieDatabase.class, "PopularMovie.db").build();
    }

    @Override
    public Single<DiscoverMovieResponse> discoverMovies(DiscoverMovieParams params) {
        return mMovieService.discoverMovies(params.getParams());
    }

    @Override
    public Single<GetMovieVideosResponse> getVideos(long movieId) {
        return mMovieService.getVideos(movieId);
    }

    @Override
    public Single<GetMovieReviewsResponse> getReviews(long movieId) {
        return mMovieService.getReviews(movieId);
    }

    @Override
    public Single<List<MovieEntity>> getFavouriteMovies() {
        return mMovieDatabase.movieDao().getMovies();
    }

    @Override
    public Maybe<MovieEntity> getFavouriteMovieById(long movieId) {
        return mMovieDatabase.movieDao().getMovieById(movieId);
    }

    @Override
    public Single<Boolean> isFavouriteMovie(long movieId) {
        return mMovieDatabase.movieDao()
                .getMovieById(movieId)
                .map(movie -> Boolean.TRUE)
                .switchIfEmpty(Maybe.defer(() -> Maybe.just(Boolean.FALSE)))
                .toSingle();
    }

    @Override
    public void addMovieToFavourite(MovieEntity movie) {
        mMovieDatabase.movieDao().insert(movie);
    }

    @Override
    public void removeMovieFromFavourite(MovieEntity movie) {
        mMovieDatabase.movieDao().delete(movie);
    }
}
