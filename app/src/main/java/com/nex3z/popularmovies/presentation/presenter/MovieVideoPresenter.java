package com.nex3z.popularmovies.presentation.presenter;

import android.util.Log;

import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoListArg;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;
import com.nex3z.popularmovies.presentation.ui.MovieVideoView;
import com.nex3z.popularmovies.presentation.util.VideoUtility;

import java.util.List;

public class MovieVideoPresenter implements Presenter {
    private static final String LOG_TAG = MovieVideoPresenter.class.getSimpleName();

    private MovieModel mMovieModel;
    private MovieVideoView mView;
    private List<VideoModel> mVideoModels;
    private UseCase mGetVideoList;
    private VideoModelDataMapper mMapper;

    public MovieVideoPresenter(MovieModel movie,
                               UseCase getVideoList, VideoModelDataMapper mapper) {
        mMovieModel = movie;
        mGetVideoList = getVideoList;
        mMapper = mapper;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mGetVideoList.unsubscribe();
    }

    public void setView(MovieVideoView view) {
        mView = view;
    }

    public void initialize() {
        hideViewRetry();
        showViewLoading();
        fetchVideos();
    }

    public void playVideo(int position) {
        VideoModel videoModel = mVideoModels.get(position);
        if (videoModel != null) {
            VideoUtility.playVideo(mView.getContext(), videoModel.getSite(), videoModel.getKey());
        }
    }

    private void showViewLoading() {
        mView.showLoading();
    }

    private void hideViewLoading() {
        mView.hideLoading();
    }

    private void showViewRetry() {
        mView.showRetry();
    }

    private void hideViewRetry() {
        mView.hideRetry();
    }

    @SuppressWarnings("unchecked")
    private void fetchVideos() {
        Log.v(LOG_TAG, "fetVideos(): movie id = " + mMovieModel.getId());
        mGetVideoList.init(new GetVideoListArg(mMovieModel.getId()))
                .execute(new VideoListSubscriber());
    }

    private void showVideoCollectionInView() {
        Log.v(LOG_TAG, "showVideoCollectionInView(): mVideoModels.size() = " + mVideoModels.size());
        mView.hideLoading();
        mView.renderVideos(mVideoModels);
    }

    private final class VideoListSubscriber extends DefaultSubscriber<List<Video>> {

        @Override public void onCompleted() {}

        @Override public void onError(Throwable e) {
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
        }

        @Override public void onNext(List<Video> videos) {
            mVideoModels = mMapper.transform(videos);
            showVideoCollectionInView();
        }
    }
}
