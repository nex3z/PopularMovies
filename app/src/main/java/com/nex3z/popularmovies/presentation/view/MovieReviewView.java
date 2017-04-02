package com.nex3z.popularmovies.presentation.view;

import com.nex3z.popularmovies.domain.model.review.ReviewModel;

import java.util.List;

public interface MovieReviewView extends BaseView, LoadDataView {

    void renderReviews(List<ReviewModel> reviews);

}
