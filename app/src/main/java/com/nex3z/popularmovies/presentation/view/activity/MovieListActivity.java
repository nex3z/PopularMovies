package com.nex3z.popularmovies.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.view.fragment.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.view.fragment.MovieListFragment;
import com.nex3z.popularmovies.presentation.view.fragment.MovieReviewFragment;
import com.nex3z.popularmovies.presentation.view.fragment.MovieVideoFragment;

public class MovieListActivity extends BaseActivity implements
        MovieListFragment.OnItemSelectListener {
    private static final String LOG_TAG = MovieListActivity.class.getSimpleName();

    private Fragment mDiscoveryFragment;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.container_movie_detail) != null) {
            mTwoPane = true;
        }

        mDiscoveryFragment = MovieListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_list_container, mDiscoveryFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemSelect(MovieModel movieModel, MovieAdapter.ViewHolder vh) {
        Log.v(LOG_TAG, "OnItemSelect(): movieModel = " + movieModel);
        if (mTwoPane) {
            MovieInfoFragment movieInfoFragment = MovieInfoFragment.newInstance(movieModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_movie_info, movieInfoFragment)
                    .commit();
            MovieVideoFragment movieVideoFragment = MovieVideoFragment.newInstance(movieModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_movie_video, movieVideoFragment)
                    .commit();
            MovieReviewFragment movieReviewFragment = MovieReviewFragment.newInstance(movieModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_movie_review, movieReviewFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .putExtra(MovieDetailActivity.MOVIE_INFO, movieModel);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, new Pair<View, String>(
                            vh.mIvPoster,
                            getString(R.string.detail_poster_transition_name)));

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        }
    }
}