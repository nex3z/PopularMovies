package com.nex3z.popularmovies.data.repository.review;

import com.nex3z.popularmovies.data.entity.review.ReviewRespEntity;

import io.reactivex.Observable;

public interface ReviewRepository {

    Observable<ReviewRespEntity> getReviews(long movieId);

}
