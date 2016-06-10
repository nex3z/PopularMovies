package com.nex3z.popularmovies.presentation.ui;

import com.nex3z.popularmovies.presentation.model.ReviewModel;

import java.util.Collection;

public interface MovieReviewView extends LoadDataView {

    void renderReviews(Collection<ReviewModel> reviews);

}
