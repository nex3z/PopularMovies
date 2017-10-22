package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.domain.model.video.VideoModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class GetVideosUseCase extends BaseUseCase<List<VideoModel>, GetVideosUseCase.Params> {

    public GetVideosUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<List<VideoModel>> buildUseCaseObservable(Params params) {
        return mContext.getMovieRepository()
                .getVideos(params.mMovieId)
                .map(VideoModelMapper::transform)
                .toObservable();
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
