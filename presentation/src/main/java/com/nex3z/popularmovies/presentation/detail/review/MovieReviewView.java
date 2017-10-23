package com.nex3z.popularmovies.presentation.detail.review;

import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.presentation.base.BaseView;
import com.nex3z.popularmovies.presentation.base.LoadDataView;

import java.util.List;

public interface MovieReviewView extends BaseView, LoadDataView {

    void renderReviews(List<ReviewModel> reviews);

}
