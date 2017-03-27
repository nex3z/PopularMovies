package com.nex3z.popularmovies.data.repository.review;

import com.nex3z.popularmovies.data.entity.review.ReviewRespEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.service.ReviewService;

import io.reactivex.Observable;

public class ReviewRepositoryImpl implements ReviewRepository {

    private ReviewService mReviewService;

    public ReviewRepositoryImpl(RestClient restClient) {
        mReviewService = restClient.getReviewService();
    }

    @Override
    public Observable<ReviewRespEntity> getReviews(long movieId) {
        return mReviewService.getReviews(movieId);
    }
}
