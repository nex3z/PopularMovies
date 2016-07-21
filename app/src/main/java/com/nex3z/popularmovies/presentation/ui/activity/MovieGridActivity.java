package com.nex3z.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieGridFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieVideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieGridActivity extends AppCompatActivity implements
        MovieGridFragment.Callbacks, NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    private boolean mTwoPane;
    private MovieGridFragment mDiscoveryFragment;
    private MovieGridFragment mFavouriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        setupDrawer();

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            navigateToDiscoveryList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.v(LOG_TAG, "onNavigationItemSelected(): id = " + id);

        if (id == R.id.nav_discover) {
            navigateToDiscoveryList();
        } else if (id == R.id.nav_favourite) {
            navigateToFavouriteList();
        } else if (id == R.id.nav_settings) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override @SuppressWarnings("unchecked")
    public void onItemSelected(MovieModel movieModel, MovieAdapter.ViewHolder vh) {
        Log.v(LOG_TAG, "onItemSelected(): movieModel = " + movieModel);

        if (mTwoPane) {
            MovieInfoFragment movieInfoFragment = MovieInfoFragment.newInstance(movieModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, movieInfoFragment)
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

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void navigateToDiscoveryList() {
        if (mDiscoveryFragment == null) {
            mDiscoveryFragment = MovieGridFragment.newDiscoveryInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_list_container, mDiscoveryFragment)
                .commit();
    }

    private void navigateToFavouriteList() {
        if (mFavouriteFragment == null) {
            mFavouriteFragment = MovieGridFragment.newFavouriteInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_list_container, mFavouriteFragment)
                .commit();
    }

}
