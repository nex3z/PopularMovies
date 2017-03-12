package com.nex3z.popularmovies.domain.interactor.video;

import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.VideoRepository;

import java.util.List;

import io.reactivex.Observable;


public class GetVideoList extends UseCase<List<Video>, GetVideoList.Params> {

    private final VideoRepository mVideoRepository;

    public GetVideoList(VideoRepository repository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mVideoRepository = repository;
    }

    @Override
    public Observable<List<Video>> buildUseCaseObservable(Params params) {
        return mVideoRepository.videos(params.mMovieId);
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
