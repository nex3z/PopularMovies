package com.nex3z.popularmovies.domain.interactor.review;

import com.nex3z.popularmovies.data.repository.review.ReviewRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.domain.model.review.ReviewModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class GetReviewUseCase extends UseCase<List<ReviewModel>, GetReviewUseCase.Params> {

    private final ReviewRepository mReviewRepository;

    public GetReviewUseCase(ReviewRepository reviewRepository,
                            ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mReviewRepository = reviewRepository;
    }

    @Override
    public Observable<List<ReviewModel>> buildUseCaseObservable(Params params) {
        return mReviewRepository
                .getReviews(params.mMovieId)
                .map(ReviewModelMapper::transform);
    }

    public static final class Params {
        private long mMovieId;

        private Params(long movieId) {
            mMovieId = movieId;
        }

        public static Params forMovie(long movieId) {
            return new Params(movieId);
        }
    }
}
