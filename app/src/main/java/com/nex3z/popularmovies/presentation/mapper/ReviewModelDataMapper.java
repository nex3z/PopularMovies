package com.nex3z.popularmovies.presentation.mapper;

import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.presentation.model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewModelDataMapper {

    public ReviewModel transform(Review review) {
        ReviewModel reviewModel = null;
        if (review != null) {
            reviewModel = new ReviewModel();
            reviewModel.setAuthor(review.getAuthor());
            reviewModel.setContent(review.getContent());
            reviewModel.setId(review.getId());
        }
        return reviewModel;
    }

    public List<ReviewModel> transform(List<Review> reviewCollection) {
        List<ReviewModel> reviewModelList = new ArrayList<>();
        ReviewModel reviewModel;
        for (Review review : reviewCollection) {
            reviewModel = transform(review);
            if (reviewModel != null) {
                reviewModelList.add(reviewModel);
            }
        }

        return reviewModelList;
    }
}
