package com.nex3z.popularmovies.domain.interactor.video;

import com.nex3z.popularmovies.data.repository.video.VideoRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.domain.model.video.VideoModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class GetVideoUseCase extends UseCase<List<VideoModel>, GetVideoUseCase.Params> {
    private final VideoRepository mVideoRepository;

    public GetVideoUseCase(VideoRepository videoRepository,
                            ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mVideoRepository = videoRepository;
    }

    @Override
    public Observable<List<VideoModel>> buildUseCaseObservable(Params params) {
        return mVideoRepository
                .getVideos(params.mMovieId)
                .map(VideoModelMapper::transform);
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
