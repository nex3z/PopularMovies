package com.nex3z.popularmovies.presentation.detail.video;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.GetVideosUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.app.App;
import com.nex3z.popularmovies.presentation.base.BasePresenter;

import java.util.List;

public class MovieVideoPresenter extends BasePresenter<MovieVideoView> {

    private final MovieModel mMovie;
    private final GetVideosUseCase mGetVideosUseCase;
    private List<VideoModel> mVideos;

    public MovieVideoPresenter(MovieModel movie) {
        mMovie = movie;
        mGetVideosUseCase = App.getPopMovieService().create(GetVideosUseCase.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        mGetVideosUseCase.dispose();
    }

    public void init() {
        fetchVideos();
    }

    public void onVideoClick(int position) {
        if (position >= 0 && position < mVideos.size()) {
            mView.playVideo(mVideos.get(position));
        }
    }

    private void fetchVideos() {
        mGetVideosUseCase.execute(new VideoObserver(),
                GetVideosUseCase.Params.forMovie(mMovie.getId()));
    }

    private class VideoObserver extends DefaultObserver<List<VideoModel>> {
        @Override
        protected void onStart() {
            super.onStart();
            mView.showLoading();
        }

        @Override
        public void onNext(List<VideoModel> videos) {
            super.onNext(videos);
            mView.hideLoading();
            mVideos = videos;
            mView.renderVideos(mVideos);
        }

        @Override
        public void onError(Throwable throwable) {
            super.onError(throwable);
            mView.showError(throwable.getMessage());
        }
    }
}
