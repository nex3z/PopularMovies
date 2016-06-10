package com.nex3z.popularmovies.presentation.presenter;

import android.util.Log;

import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewListArg;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.ui.MovieReviewView;

import java.util.List;

public class MovieReviewPresenter implements Presenter {
    private static final String LOG_TAG = MovieReviewPresenter.class.getSimpleName();

    private MovieModel mMovieModel;
    private MovieReviewView mView;
    private List<ReviewModel> mReviewModels;
    private UseCase mGetReviewList;
    private ReviewModelDataMapper mMapper;

    public MovieReviewPresenter(MovieModel movie,
                                UseCase getReviewList, ReviewModelDataMapper mapper) {
        mMovieModel = movie;
        mGetReviewList = getReviewList;
        mMapper = mapper;
    }

    public void initialize() {
        hideViewRetry();
        showViewLoading();
        fetchReviews();
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mGetReviewList.unsubscribe();
    }

    public void setView(MovieReviewView view) {
        mView = view;
    }

    private void showViewLoading() {
        mView.showLoading();
    }

    private void hideViewLoading() {
        mView.hideLoading();
    }

    private void showViewRetry() {
        mView.showRetry();
    }

    private void hideViewRetry() {
        mView.hideRetry();
    }

    @SuppressWarnings("unchecked")
    private void fetchReviews() {
        Log.v(LOG_TAG, "fetVideos(): movie id = " + mMovieModel.getId());
        mGetReviewList.init(new GetReviewListArg(mMovieModel.getId()))
                .execute(new ReviewListSubscriber());
    }

    private void showReviewCollectionInView() {
        Log.v(LOG_TAG, "showReviewCollectionInView(): mReviewModels.size() = "
                + mReviewModels.size());
        mView.hideLoading();
        mView.renderReviews(mReviewModels);
    }

    private final class ReviewListSubscriber extends DefaultSubscriber<List<Review>> {

        @Override public void onCompleted() {}

        @Override public void onError(Throwable e) {
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
        }

        @Override public void onNext(List<Review> reviews) {
            mReviewModels = mMapper.transform(reviews);
            showReviewCollectionInView();
        }
    }
}
