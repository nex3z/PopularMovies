package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;

import rx.Observable;

public class GetReviewList extends UseCase<GetReviewListArg> {

    private final ReviewRepository mReviewRepository;

    public GetReviewList(ReviewRepository repository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mReviewRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mArg == null) {
            throw new IllegalArgumentException("mArg cannot be null.");
        }

        return mReviewRepository.reviews(mArg.getMovieId());
    }
}
