package com.nex3z.popularmovies.data.entity.mapper;

import com.nex3z.popularmovies.data.entity.ReviewEntity;
import com.nex3z.popularmovies.domain.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewEntityDataMapper {

    public Review transform(ReviewEntity reviewEntity) {
        Review review = null;
        if (reviewEntity != null) {
            review = new Review();
            review.setAuthor(reviewEntity.getAuthor());
            review.setContent(reviewEntity.getContent());
            review.setId(reviewEntity.getId());
        }
        return review;
    }

    public List<Review> transform(List<ReviewEntity> reviewEntityCollection) {
        List<Review> reviewList = new ArrayList<>();
        Review review;
        for (ReviewEntity reviewEntity : reviewEntityCollection) {
            review = transform(reviewEntity);
            if (review != null) {
                reviewList.add(review);
            }
        }

        return reviewList;
    }
}
