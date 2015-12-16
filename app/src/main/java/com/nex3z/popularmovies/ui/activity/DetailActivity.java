package com.nex3z.popularmovies.ui.activity;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.ui.fragment.MovieDetailFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_INFO = "MOVIE_INFO";

    private int mParallaxImageHeight;
    private Movie mMovie;

    @Bind(R.id.detail_activity_toolbar) Toolbar mToolbar;
    @Bind(R.id.detail_activity_backdrop_image) ImageView mBackdropImage;
    @Bind(R.id.detail_activity_play_btn) ImageView mPlayBtn;
    @Bind(R.id.detail_activity_scroll) ObservableScrollView mScrollView;

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

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.backdrop_image_height);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(MOVIE_INFO);
            Log.v(LOG_TAG, "onCreate(): savedInstanceState == null, movie = " + mMovie);

            MovieDetailFragment fragment = MovieDetailFragment.newInstance(mMovie);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_activity_fragment_container, fragment)
                    .commit();

        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.v(LOG_TAG, "onScrollChanged(): scrollY = " + scrollY + ", firstScroll = " + firstScroll
                + ", dragging = " + dragging);
//        int baseColor = getResources().getColor(R.color.color_primary);
//        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);

        //mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));

        mBackdropImage.setTranslationY(scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        Log.v(LOG_TAG, "onUpOrCancelMotionEvent(): scrollState = " + scrollState);
        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.color_primary));
                showToolbar();
            }
        }
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
//                mScrollView.setTranslationY(translationY);
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) mScrollView).getLayoutParams();
//                lp.height = (int) - translationY + getScreenHeight() - lp.topMargin;
//                ((View) mScrollView).requestLayout();
            }
        });
        animator.start();
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
