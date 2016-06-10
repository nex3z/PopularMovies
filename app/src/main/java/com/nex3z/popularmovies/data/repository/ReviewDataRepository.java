package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.mapper.ReviewEntityDataMapper;
import com.nex3z.popularmovies.data.repository.datasource.review.ReviewDataStore;
import com.nex3z.popularmovies.data.repository.datasource.review.ReviewDataStoreFactory;
import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;

import java.util.List;

import rx.Observable;

public class ReviewDataRepository implements ReviewRepository {
    private final ReviewDataStoreFactory mReviewDataStoreFactory;
    private final ReviewEntityDataMapper mReviewEntityDataMapper;

    public ReviewDataRepository(ReviewDataStoreFactory factory, ReviewEntityDataMapper mapper) {
        mReviewDataStoreFactory = factory;
        mReviewEntityDataMapper = mapper;
    }

    @Override
    public Observable<List<Review>> reviews(long movieId) {
        final ReviewDataStore reviewDataStore =
                mReviewDataStoreFactory.createCloudReviewDataStore();
        return reviewDataStore.reviewEntityList(movieId).map(mReviewEntityDataMapper::transform);
    }

}
