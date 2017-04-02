package com.nex3z.popularmovies.presentation.presenter;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.presentation.view.MovieReviewView;

import java.util.List;

public class MovieReviewPresenter implements Presenter {
    private static final String LOG_TAG = MovieReviewPresenter.class.getSimpleName();

    private MovieReviewView mView;
    private MovieModel mMovie;
    private GetReviewUseCase mGetReviewUseCase;

    public MovieReviewPresenter(MovieModel movie, GetReviewUseCase useCase) {
        mMovie = movie;
        mGetReviewUseCase = useCase;
    }

    public void setView(MovieReviewView view) {
        mView = view;
    }

    public void init() {
        mView.showLoading();
        mGetReviewUseCase.execute(new ReviewObserver(),
                GetReviewUseCase.Params.forMovie(mMovie.getId()));
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mView = null;
        mGetReviewUseCase.dispose();
    }

    private final class ReviewObserver extends DefaultObserver<List<ReviewModel>> {
        @Override
        public void onNext(List<ReviewModel> reviews) {
            mView.hideLoading();
            mView.renderReviews(reviews);
        }

        @Override
        public void onError(Throwable exception) {
            exception.printStackTrace();
            mView.hideLoading();
        }
    }
}
