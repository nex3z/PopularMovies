package com.nex3z.popularmovies.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.ui.fragment.MovieDetailFragment;
import com.nex3z.popularmovies.ui.fragment.VideoListFragment;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    Uri mDetailUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            mDetailUri = getIntent().getData();
            Log.v(LOG_TAG, "onCreate(): mDetailUri = " + mDetailUri);
//            Bundle arguments = new Bundle();
//            arguments.putParcelable(MovieDetailFragment.DETAIL_URI, getIntent().getData());
//
//            MovieDetailFragment fragment = new MovieDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.movie_detail_container, fragment)
//                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] {
                getString(R.string.tab_movie_detail),
                getString(R.string.tab_movie_video)};

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.DETAIL_URI, mDetailUri);

                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);

                    return fragment;
                }
                case 1: {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(VideoListFragment.DETAIL_URI, mDetailUri);

                    VideoListFragment fragment = new VideoListFragment();
                    fragment.setArguments(arguments);

                    return fragment;
                }
                default:
                    return new MovieDetailFragment();
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

    void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
