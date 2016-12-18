package com.nex3z.popularmovies.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.internal.di.HasComponent;
import com.nex3z.popularmovies.presentation.internal.di.component.DaggerMovieDetailComponent;
import com.nex3z.popularmovies.presentation.internal.di.component.MovieDetailComponent;
import com.nex3z.popularmovies.presentation.internal.di.module.MovieDetailModule;
import com.nex3z.popularmovies.presentation.internal.di.module.MovieModule;
import com.nex3z.popularmovies.presentation.internal.di.module.ReviewModule;
import com.nex3z.popularmovies.presentation.internal.di.module.VideoModule;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieDetailPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieDetailView;
import com.nex3z.popularmovies.presentation.ui.MovieGridView;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieReviewFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieVideoFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView,
        HasComponent<MovieDetailComponent> {
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private static final String SAVE_TAG_MOVIE = "movie";

    public static final String MOVIE_INFO = "movie_info";

    @BindView(R.id.tab_detail_container) TabLayout mTabLayout;
    @BindView(R.id.pager_detail_container) ViewPager mViewPager;
    @BindView(R.id.iv_detail_backdrop) ImageView mIvBackdropImage;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout mCollapsingToolBarLayout;
    @BindView(R.id.app_bar) AppBarLayout mAppBarLayout;

    @Inject MovieDetailPresenter mPresenter;

    private MovieDetailComponent mMovieDetailComponent;
    private MovieModel mMovie;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(MOVIE_INFO);
        } else {
            mMovie = savedInstanceState.getParcelable(SAVE_TAG_MOVIE);
        }

        initInjector();

        supportPostponeEnterTransition();

        initialize();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieGridView.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mMovie != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVE_TAG_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }


    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovie.getTitle() + ": " + mMovie.getOverview()
                + getString(R.string.share_hash_tag));
        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void renderMovie(MovieModel movie) {
        mCollapsingToolBarLayout.setTitle(movie.getTitle());
        Picasso.with(this)
                .load(mMovie.getBackdropImageUrl())
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(mIvBackdropImage);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showRetry() {}

    @Override
    public void hideRetry() {}

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public MovieDetailComponent getComponent() {
        return mMovieDetailComponent;
    }

    @OnClick(R.id.ibtn_detail_play)
    public void play() {
        mPresenter.playInitialVideo();
    }

    private void initInjector() {
        mMovieDetailComponent = DaggerMovieDetailComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .movieModule(new MovieModule(mMovie))
                .videoModule(new VideoModule(mMovie))
                .reviewModule(new ReviewModule(mMovie))
                .movieDetailModule(new MovieDetailModule(mMovie))
                .build();
        mMovieDetailComponent.inject(this);
    }

    private void initialize() {
        initializeTabLayout();
        mPresenter.setView(this);
        mPresenter.initialize();
    }

    private void initializeTabLayout() {
        mViewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (mCollapsingToolBarLayout.getHeight() + verticalOffset <
                    mCollapsingToolBarLayout.getHeight() / 2) {
                mTabLayout.setVisibility(View.GONE);
            } else {
                mTabLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    class SectionPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;

        private String tabTitles[] = new String[]{
                getString(R.string.tab_movie_detail),
                getString(R.string.tab_movie_video),
                getString(R.string.tab_movie_review)};

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(LOG_TAG, "getItem(): position = " + position);
            switch (position) {
                case 0: {
                    return MovieInfoFragment.newInstance(mMovie);
                }
                case 1: {
                    return MovieVideoFragment.newInstance(mMovie);
                }
                case 2: {
                    return MovieReviewFragment.newInstance(mMovie);
                }
                default:
                    return MovieInfoFragment.newInstance(mMovie);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
