package com.nex3z.popularmovies.presentation.detail;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.MainActivity;
import com.nex3z.popularmovies.presentation.detail.info.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.detail.review.MovieReviewFragment;
import com.nex3z.popularmovies.presentation.detail.video.MovieVideoFragment;
import com.nex3z.popularmovies.presentation.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.ctl_movie_detail) CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tb_movie_detail) Toolbar mToolBar;
    @BindView(R.id.abl_movie_detail) AppBarLayout mAppBar;
    @BindView(R.id.tab_movie_detail_page) TabLayout mTabLayout;
    @BindView(R.id.vp_movie_detail_page_container) ViewPager mPager;
    @BindView(R.id.sdv_movie_detail_backdrop) SimpleDraweeView mSdvBackdrop;
    @BindView(R.id.btn_movie_detail_play) ImageButton mBtnPlay;

    private MovieModel mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(ARG_MOVIE);
        } else {
            mMovie = savedInstanceState.getParcelable(ARG_MOVIE);
        }

        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mMovie != null) {
            shareActionProvider.setShareIntent(createShareMovieIntent());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        initPager();
        renderMovie();
    }

    private void renderMovie() {
        mCollapsingToolbar.setTitle(mMovie.getTitle());
        ViewUtil.loadProgressiveImage(mSdvBackdrop, 
                mMovie.getBackdropUrl(MovieModel.BACKDROP_SIZE_W780));
    }

    private void initPager() {
        mPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);

        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (mCollapsingToolbar.getHeight() + verticalOffset <
                    mCollapsingToolbar.getHeight() / 2) {
                mTabLayout.setVisibility(View.GONE);
            } else {
                mTabLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovie.getTitle() + ": " + mMovie.getOverview()
                + getString(R.string.caption_share_hash_tag));
        return shareIntent;
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {
        private static final int PAGE_COUNT = 3;

        private String tabTitles[] = new String[]{
                getString(R.string.caption_tab_movie_detail),
                getString(R.string.caption_tab_movie_video),
                getString(R.string.caption_tab_movie_review)};

        SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
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
