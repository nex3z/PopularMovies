package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.model.review.ReviewModelMapper;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;

import java.util.List;

import io.reactivex.Observable;

public class GetReviewsUseCase extends BaseUseCase<List<ReviewModel>, GetReviewsUseCase.Params> {

    public GetReviewsUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<List<ReviewModel>> buildUseCaseObservable(Params params) {
        return mContext.getMovieRepository()
                .getReviews(params.mMovieId)
                .map(ReviewModelMapper::transform)
                .toObservable();
    }

    public static class Params {
        private final long mMovieId;

        private Params(long movieId) {
            mMovieId = movieId;
        }

        public Params forMovie(long movieId) {
            return new Params(movieId);
        }
    }
}
