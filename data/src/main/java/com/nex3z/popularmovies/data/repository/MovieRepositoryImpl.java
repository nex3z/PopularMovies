package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieResponse;
import com.nex3z.popularmovies.data.entity.review.GetMovieReviewsResponse;
import com.nex3z.popularmovies.data.entity.video.GetMovieVideosResponse;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.MovieService;

import java.util.Map;

import io.reactivex.Single;

public class MovieRepositoryImpl implements MovieRepository {

    private MovieService mMovieService;

    public MovieRepositoryImpl(RestClient restClient) {
        mMovieService = restClient.getMovieService();
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

}
