package com.nex3z.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieDetailPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieDetailView;
import com.nex3z.popularmovies.presentation.ui.MovieGridView;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieReviewFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieVideoFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView {
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_INFO = "movie_info";

    @BindView(R.id.tab_detail_container) TabLayout mTabLayout;
    @BindView(R.id.pager_detail_container) ViewPager mViewPager;
    @BindView(R.id.iv_detail_backdrop) ImageView mIvBackdropImage;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout mCollapsingToolBarLayout;
    @BindView(R.id.app_bar) AppBarLayout mAppBarLayout;

    private MovieModel mMovie;
    private MovieDetailPresenter mPresenter;

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
            Log.v(LOG_TAG, "onCreate(): savedInstanceState == null, movie = " + mMovie);
        }

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
    public void renderMovie(MovieModel movie) {
        mCollapsingToolBarLayout.setTitle(movie.getTitle());
        Picasso.with(this)
                .load(mMovie.getBackdropImageUrl())
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(mIvBackdropImage);
    }

    private void initialize() {
        initializeTabLayout();

        mPresenter = new MovieDetailPresenter();
        mPresenter.setMovie(mMovie);
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
