package com.nex3z.popularmovies.presentation.presenter;

import android.util.Log;

import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewListArg;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoListArg;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;
import com.nex3z.popularmovies.presentation.ui.IntegratedMovieInfoView;
import com.nex3z.popularmovies.presentation.util.VideoUtility;

import java.util.List;

public class IntegratedMovieInfoPresenter implements Presenter {
    private static final String LOG_TAG = MovieVideoPresenter.class.getSimpleName();

    private IntegratedMovieInfoView mView;

    private MovieModel mMovieModel;

    private List<VideoModel> mVideoModels;
    private UseCase mGetVideoList;
    private VideoModelDataMapper mVideoMapper;

    private List<ReviewModel> mReviewModels;
    private UseCase mGetReviewList;
    private ReviewModelDataMapper mReviewMapper;

    public IntegratedMovieInfoPresenter(MovieModel movie,
                                        UseCase getVideoList, VideoModelDataMapper videoMapper,
                                        UseCase getReviewList, ReviewModelDataMapper reviewMapper) {
        mMovieModel = movie;
        mGetVideoList = getVideoList;
        mVideoMapper = videoMapper;
        mGetReviewList = getReviewList;
        mReviewMapper = reviewMapper;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mGetVideoList.unsubscribe();
        mGetReviewList.unsubscribe();
    }

    public void setView(IntegratedMovieInfoView view) {
        mView = view;
    }

    public void initialize() {
        mView.renderMovie(mMovieModel);

        hideViewRetry();
        showViewLoading();
        fetchVideos();
        fetchReviews();
    }

    public void playVideo(VideoModel videoModel) {
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
        hideViewLoading();
        mView.renderVideos(mVideoModels);
    }

    private final class VideoListSubscriber extends DefaultSubscriber<List<Video>> {

        @Override public void onError(Throwable e) {
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
        }

        @Override public void onNext(List<Video> videos) {
            mVideoModels = mVideoMapper.transform(videos);
            showVideoCollectionInView();
        }
    }

    @SuppressWarnings("unchecked")
    private void fetchReviews() {
        Log.v(LOG_TAG, "fetVideos(): movie id = " + mMovieModel.getId());
        mGetReviewList.init(new GetReviewListArg(mMovieModel.getId()))
                .execute(new ReviewListSubscriber());
    }

    private void showReviewCollectionInView() {
        Log.v(LOG_TAG, "showReviewCollectionInView(): mReviewModels.size() = "
                + mReviewModels.size());
        hideViewLoading();
        mView.renderReviews(mReviewModels);
    }

    private final class ReviewListSubscriber extends DefaultSubscriber<List<Review>> {

        @Override public void onCompleted() {}

        @Override public void onError(Throwable e) {
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
        }

        @Override public void onNext(List<Review> reviews) {
            mReviewModels = mReviewMapper.transform(reviews);
            showReviewCollectionInView();
        }
    }
}
