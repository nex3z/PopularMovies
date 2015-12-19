package com.nex3z.popularmovies.ui.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.model.Video;
import com.nex3z.popularmovies.data.rest.model.VideoResponse;
import com.nex3z.popularmovies.data.rest.service.VideoService;
import com.nex3z.popularmovies.ui.fragment.MovieDetailFragment;
import com.nex3z.popularmovies.util.ImageUtility;
import com.nex3z.popularmovies.util.VideoUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_INFO = "MOVIE_INFO";

    //private int mParallaxImageHeight;
    private Movie mMovie;
    private List<Video> mVideos = new ArrayList<Video>();
    private boolean isStatusBarTransparent = true;
    private int mToolbarHeight;
    private int mShowStatusBarColorThreshold;

    @Bind(R.id.detail_activity_toolbar) Toolbar mToolbar;
    @Bind(R.id.detail_activity_container_frame) FrameLayout mActivityContainerFrame;
    @Bind(R.id.detail_activity_backdrop_container) FrameLayout mBackdropContainerFrame;
    @Bind(R.id.detail_activity_backdrop_image) ImageView mBackdropImage;
    @Bind(R.id.detail_activity_play_btn) ImageView mPlayBtn;
    @Bind(R.id.detail_activity_scroll) ObservableScrollView mScrollView;
    @BindColor(R.color.color_primary) int mColorPrimary;
    @BindColor(R.color.color_primary_dark) int mColorPrimaryDark;

    @BindDimen(R.dimen.detail_poster_margin) int mPosterMargin;
    @BindDimen(R.dimen.backdrop_image_height) int mParallaxImageHeight;
//    @BindDimen(R.attr.actionBarSize) int mToolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mScrollView.setScrollViewCallbacks(this);

        //mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.backdrop_image_height);
        Log.v(LOG_TAG, "onCreate(): mParallaxImageHeight = " + mParallaxImageHeight + ", mPosterMargin = " + mPosterMargin);

        mToolbar.setBackgroundColor(Color.TRANSPARENT);
//        mToolbarHeight = mToolbar.getMeasuredHeight();

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(MOVIE_INFO);
            Log.v(LOG_TAG, "onCreate(): savedInstanceState == null, movie = " + mMovie);

            MovieDetailFragment fragment = MovieDetailFragment.newInstance(mMovie);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_activity_fragment_container, fragment)
                    .commit();
            updateMovieInfo(mMovie);
        }

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mToolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        mShowStatusBarColorThreshold = mParallaxImageHeight + mPosterMargin - mToolbarHeight;
        Log.v(LOG_TAG, "onCreate(): mShowStatusBarColorThreshold = " + mShowStatusBarColorThreshold);

        mPlayBtn.setOnClickListener(view -> {
                Video video = mVideos.get(0);
                if (video.getKey() != null) {
                    VideoUtility.playVideo(DetailActivity.this,
                            video.getSite(), video.getKey());
                }
            }
        );

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.v(LOG_TAG, "onScrollChanged(): scrollY = " + scrollY + ", firstScroll = " + firstScroll
                + ", dragging = " + dragging);

        if(scrollY >= mShowStatusBarColorThreshold)  {
            if (isStatusBarTransparent) {
                Log.v(LOG_TAG, "onScrollChanged(): show status bar color");
                animateStatusBarAlpha(0, 1);
                isStatusBarTransparent = false;
            }

            int toolbarOffset = scrollY - mShowStatusBarColorThreshold;
            toolbarOffset = toolbarOffset > mToolbarHeight ? mToolbarHeight : toolbarOffset;
            Log.v(LOG_TAG, "onScrollChanged(): mToolbar.getTranslationY() = " + mToolbar.getTranslationY() + "mToolbar.setTranslationY = " + (-toolbarOffset));
            mToolbar.setTranslationY(-toolbarOffset);

        } else {
            if (!isStatusBarTransparent) {
                Log.v(LOG_TAG, "onScrollChanged(): hide status bar color");
                animateStatusBarAlpha(1, 0);
                isStatusBarTransparent = true;
            }
            mToolbar.setTranslationY(0);
        }

        mBackdropContainerFrame.setTranslationY(scrollY / 2);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateStatusBarAlpha(float fromAlpha, float toAlpha) {
        Window window = this.getWindow();

        ValueAnimator animator = ValueAnimator.ofFloat(fromAlpha, toAlpha).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                int color = ScrollUtils.getColorWithAlpha(alpha, mColorPrimaryDark);
                window.setStatusBarColor(color);
            }
        });
        animator.start();
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        int y = mScrollView.getCurrentScrollY();
        Log.v(LOG_TAG, "onUpOrCancelMotionEvent(): mScrollView.getCurrentScrollY() = " + y);
//        Log.v(LOG_TAG, "onUpOrCancelMotionEvent(): scrollState = " + scrollState);
//        if (scrollState == ScrollState.UP) {
//            if (toolbarIsShown()) {
//                hideToolbar();
//            }
//        } else if (scrollState == ScrollState.DOWN) {
//            if (toolbarIsHidden()) {
//                mToolbar.setBackgroundColor(mColorPrimary);
//                showToolbar();
//            }
//        }
    }

    private boolean toolbarIsShown() {
        return mToolbar.getTranslationY() == 0;
    }

    private boolean toolbarIsHidden() {
        return mToolbar.getTranslationY() == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        Log.v(LOG_TAG, "hideToolbar(): mToolbar.getHeight() = " + -mToolbar.getHeight());
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        Log.v(LOG_TAG, "moveToolbar(): toTranslationY = " + toTranslationY);
        if (mToolbar.getTranslationY() == toTranslationY) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(mToolbar.getTranslationY(), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                mToolbar.setTranslationY(translationY);
            }
        });
        animator.start();
    }

    private void updateMovieInfo(Movie movie) {
        getSupportActionBar().setTitle(mMovie.getTitle());

        String url = ImageUtility.getImageUrl(movie.getBackdropPath());
        Log.v(LOG_TAG, "updateBackdropImage(): backdrop url = " + url
                + ", mBackdropImage = " + mBackdropImage);
        Picasso.with(this).load(url).into(mBackdropImage);
        fetchVideos(movie.getId());
    }

    public void fetchVideos(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        VideoService service = App.getRestClient().getVideoService();
        service.getVideos(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processVideoResponse(response),
                        throwable -> Snackbar.make(
                                mActivityContainerFrame,
                                throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG
                        ).show()
                );
    }

    private void processVideoResponse(VideoResponse response) {
        List<Video> videos = response.getVideos();
        if (videos.size() != 0) {
            Log.v(LOG_TAG, "processVideoResponse(): videos size = " + videos.size());
            mVideos.addAll(videos);
            Log.v(LOG_TAG, "processVideoResponse(): mVideos size = " + mVideos.size());
            for(Video video : mVideos) {
                Log.v(LOG_TAG, "processVideoResponse(): video key = " + video.getKey()
                        + ", name = " + video.getName());
            }
            Log.v(LOG_TAG, "processVideoResponse(): size = " + response.getVideos().size());
            mPlayBtn.setVisibility(View.VISIBLE);
        }
    }
}
