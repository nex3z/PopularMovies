package com.nex3z.popularmovies.domain.interactor.review;

import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;

import java.util.List;

import io.reactivex.Observable;

public class GetReviewList extends UseCase<List<Review>, GetReviewList.Params> {

    private final ReviewRepository mReviewRepository;

    public GetReviewList(ReviewRepository repository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mReviewRepository = repository;
    }

    @Override
    public Observable<List<Review>> buildUseCaseObservable(Params params) {
        return mReviewRepository.reviews(params.mMovieId);
    }

    public static class Params {
        private final long mMovieId;

        private Params(long movieId) {
            mMovieId = movieId;
        }

        public static Params forMovie(long movieId) {
            return new Params(movieId);
        }

    }
}
