package com.nex3z.popularmovies.presentation.presenter;


import android.util.Log;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoListArg;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;
import com.nex3z.popularmovies.presentation.ui.MovieDetailView;
import com.nex3z.popularmovies.presentation.util.VideoUtility;

import java.util.List;

public class MovieDetailPresenter implements Presenter{
    private static final String LOG_TAG = MovieDetailPresenter.class.getSimpleName();

    private MovieDetailView mView;
    private MovieModel mMovieModel;
    private List<VideoModel> mVideoModels;
    private UseCase mGetVideoList;
    private VideoModelDataMapper mMapper;

    public MovieDetailPresenter(MovieModel movie,
                                UseCase getVideoList, VideoModelDataMapper mapper) {
        mMovieModel = movie;
        mGetVideoList = getVideoList;
        mMapper = mapper;
    }

    public void setView(MovieDetailView view) {
        mView = view;
    }

    public void setMovie(MovieModel movie) {
        mMovieModel = movie;
    }

    public void initialize() {
        mView.renderMovie(mMovieModel);
        fetchVideos();
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {}

    public void playInitialVideo() {
        if (mVideoModels != null) {
            VideoModel videoModel = mVideoModels.get(0);
            if (videoModel != null) {
                VideoUtility.playVideo(mView.getContext(), videoModel.getSite(),
                        videoModel.getKey());
            } else {
                mView.showError(mView.getContext().getString(R.string.no_video_available));
            }
        } else {
            mView.showError(mView.getContext().getString(R.string.downloading_video_list));
        }
    }

    @SuppressWarnings("unchecked")
    private void fetchVideos() {
        Log.v(LOG_TAG, "fetVideos(): movie id = " + mMovieModel.getId());
        mGetVideoList.init(new GetVideoListArg(mMovieModel.getId()))
                .execute(new VideoListSubscriber());
    }

    private final class VideoListSubscriber extends DefaultSubscriber<List<Video>> {

        @Override public void onCompleted() {}

        @Override public void onError(Throwable e) {
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
        }

        @Override public void onNext(List<Video> videos) {
            mVideoModels = mMapper.transform(videos);
        }
    }
}
