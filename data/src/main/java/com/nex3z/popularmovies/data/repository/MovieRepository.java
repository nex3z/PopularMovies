package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieResponse;
import com.nex3z.popularmovies.data.entity.review.GetMovieReviewsResponse;
import com.nex3z.popularmovies.data.entity.video.GetMovieVideosResponse;

import java.util.Map;

import io.reactivex.Single;

public interface MovieRepository {

    Single<DiscoverMovieResponse> discoverMovies(DiscoverMovieParams params);

    Single<GetMovieVideosResponse> getVideos(long movieId);

    Single<GetMovieReviewsResponse> getReviews(long movieId);

}
