package com.nex3z.popularmovies.presentation.presenter;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.util.VideoUtility;
import com.nex3z.popularmovies.presentation.view.MovieVideoView;

import java.util.List;

public class MovieVideoPresenter implements Presenter {
    private static final String LOG_TAG = MovieVideoPresenter.class.getSimpleName();

    private MovieVideoView mView;
    private List<VideoModel> mVideos;
    private MovieModel mModel;
    private GetVideoUseCase mGetVideoUseCase;

    public MovieVideoPresenter(MovieModel movie, GetVideoUseCase useCase) {
        mModel = movie;
        mGetVideoUseCase = useCase;
    }

    public void setView(MovieVideoView view) {
        mView = view;
    }

    public void init() {
        mView.showLoading();
        mGetVideoUseCase.execute(new VideoObserver(),
                GetVideoUseCase.Params.forMovie(mModel.getId()));
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mView = null;
        mGetVideoUseCase.dispose();
    }

    public void playVideo(int position) {
        VideoModel videoModel = mVideos.get(position);
        if (videoModel != null) {
            VideoUtility.playVideo(mView.getContext(), videoModel.getSite(), videoModel.getKey());
        }
    }

    private final class VideoObserver extends DefaultObserver<List<VideoModel>> {
        @Override
        public void onNext(List<VideoModel> videoModels) {
            mVideos = videoModels;
            mView.renderVideos(mVideos);
            mView.hideLoading();
        }

        @Override
        public void onError(Throwable exception) {
            exception.printStackTrace();
            mView.hideLoading();
        }
    }

}
