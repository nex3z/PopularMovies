package com.nex3z.popularmovies.domain.interactor.video;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.VideoRepository;

import rx.Observable;

public class GetVideoList extends UseCase<GetVideoListArg> {

    private final VideoRepository mVideoRepository;

    public GetVideoList(VideoRepository repository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mVideoRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mArg == null) {
            throw new IllegalArgumentException("mArg cannot be null.");
        }

        return mVideoRepository.videos(mArg.getMovieId());
    }

}
