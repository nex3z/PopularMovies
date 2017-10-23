package com.nex3z.popularmovies.presentation.detail.review;

import android.util.Log;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.GetReviewsUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.presentation.app.App;
import com.nex3z.popularmovies.presentation.base.BasePresenter;

import java.util.List;

public class MovieReviewPresenter extends BasePresenter<MovieReviewView> {

    private final MovieModel mMovie;
    private final GetReviewsUseCase mGetReviewsUseCase;
    private List<ReviewModel> mReviews;

    public MovieReviewPresenter(MovieModel movie) {
        mMovie = movie;
        mGetReviewsUseCase = App.getPopMovieService().create(GetReviewsUseCase.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        mGetReviewsUseCase.dispose();
    }

    public void init() {
        fetchReviews();
    }

    private void fetchReviews() {
        mGetReviewsUseCase.execute(new ReviewObserver(),
                GetReviewsUseCase.Params.forMovie(mMovie.getId()));
    }

    private class ReviewObserver extends DefaultObserver<List<ReviewModel>> {
        @Override
        protected void onStart() {
            super.onStart();
            mView.showLoading();
        }

        @Override
        public void onNext(List<ReviewModel> review) {
            super.onNext(review);
            mView.hideLoading();
            Log.v("xxx", "review = " + review.size());
            mReviews = review;
            mView.renderReviews(mReviews);
        }

        @Override
        public void onError(Throwable throwable) {
            super.onError(throwable);
            mView.hideLoading();
            mView.showError(throwable.getMessage());
        }
    }
}
