package com.nex3z.popularmovies.data.repository.datasource.review;

import com.nex3z.popularmovies.data.entity.ReviewEntity;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.net.response.ReviewResponse;
import com.nex3z.popularmovies.data.net.service.ReviewService;

import java.util.List;

import rx.Observable;

public class CloudReviewDataStore implements ReviewDataStore {
    private RestClient mRestClient;

    public CloudReviewDataStore(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public Observable<List<ReviewEntity>> reviewEntityList(long movieId) {
        ReviewService reviewService = mRestClient.getReviewService();
        return reviewService.getReviews(movieId).map(ReviewResponse::getReviews);
    }

}
