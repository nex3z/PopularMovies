package com.nex3z.popularmovies.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.rest.model.ReviewResponse;
import com.nex3z.popularmovies.data.rest.model.VideoResponse;
import com.nex3z.popularmovies.data.rest.service.ReviewService;
import com.nex3z.popularmovies.data.rest.service.VideoService;
import com.nex3z.popularmovies.util.ImageUtility;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "MOVIE_INFO";

    private Movie mMovie;

    ImageView mBackdropImage;
    CollapsingToolbarLayout mAppBarLayout;
    @Bind(R.id.detail_title_textview) TextView mTitleView;
    @Bind(R.id.detail_release_date_textview) TextView mReleaseDateView;
    @Bind(R.id.detail_rate_textview) TextView mRateView;
    @Bind(R.id.detail_overview_textview) TextView mOverviewView;
    @Bind(R.id.detail_poster_imageview) ImageView mPosterView;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE_INFO)) {
            mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
            Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);

            Activity activity = this.getActivity();
            mAppBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            mBackdropImage = (ImageView) activity.findViewById(R.id.detail_backdrop_image);
            if (mAppBarLayout != null) {
                mAppBarLayout.setTitle(mMovie.getTitle());
            }
            updateBackdropImage();
            fetchVideos(mMovie.getId());
            fetchReviews(mMovie.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        updateDetail();
        return rootView;
    }

    private void updateBackdropImage() {
        String url = ImageUtility.getImageUrl(mMovie.getBackdropPath());
        Log.v(LOG_TAG, "updateBackdropImage(): backdrop url = " + url
                + ", mBackdropImage = " + mBackdropImage);

        if (mBackdropImage != null) {
            Picasso.with(getActivity())
                    .load(url)
                    .into(mBackdropImage);
        }
    }

    private void updateDetail() {
        mTitleView.setText(mMovie.getTitle());
        mReleaseDateView.setText(mMovie.getReleaseDate());
        mRateView.setText(mMovie.getVoteAverage() + "/10");
        mOverviewView.setText(mMovie.getOverview());

        String url = ImageUtility.getImageUrl(mMovie.getPosterPath());
        Picasso.with(getActivity())
                .load(url)
                .into(mPosterView);
    }

    public void fetchVideos(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        VideoService service = App.getRestClient().getVideoService();
        service.getVideos(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> processVideoResponse(response));
    }

    public void fetchReviews(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        ReviewService service = App.getRestClient().getReviewService();
        service.getReviews(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> processReviewResponse(response));
    }

    private void processVideoResponse(VideoResponse response) {
        Log.v(LOG_TAG, "processVideoResponse(): size = " + response.getVideos().size());

    }

    private void processReviewResponse(ReviewResponse response) {
        Log.v(LOG_TAG, "processReviewResponse(): size = " + response.getReviews().size());

    }
}
