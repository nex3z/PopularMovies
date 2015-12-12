package com.nex3z.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.ui.adapter.PosterAdapter;
import com.nex3z.popularmovies.ui.fragment.FavouriteFragment;
import com.nex3z.popularmovies.ui.fragment.MovieGridFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MovieGridActivity extends AppCompatActivity implements MovieGridFragment.Callbacks,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private boolean mTwoPane;
    private int mLastId = -1;

    private MovieGridFragment mMovieGridFragment;
    private FavouriteFragment mFavouriteFragment;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
        Log.v(LOG_TAG, "onCreate(): mTwoPane = " + mTwoPane);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem defaultMenuItem = navigationView.getMenu().findItem(R.id.nav_discover);
        onNavigationItemSelected(defaultMenuItem);
        defaultMenuItem.setChecked(true);
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
        if(mLastId == id) {
            mDrawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            mLastId = id;
        }

        if (id == R.id.nav_discover) {
            if (mMovieGridFragment == null) {
                mMovieGridFragment = new MovieGridFragment();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, mMovieGridFragment)
                    .commit();
            setTitle(getString(R.string.discover));
        } else if (id == R.id.nav_favourite) {
            if (mFavouriteFragment == null) {
                mFavouriteFragment = new FavouriteFragment();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, mFavouriteFragment)
                    .commit();
            setTitle(getString(R.string.favourite));
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(Movie movie, PosterAdapter.ViewHolder vh) {
        if (mTwoPane) {
//            Bundle args = new Bundle();
//            args.putParcelable(MovieDetailFragment.DETAIL_URI, uri);
//
//            MovieDetailFragment fragment = new MovieDetailFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
//                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .putExtra(MovieDetailActivity.MOVIE_INFO, movie);

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            new Pair<View, String>(vh.posterImageView, getString(R.string.detail_poster_transition_name)));
            ActivityOptionsCompat activityOptions2 =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this);

            ActivityOptionsCompat activityOptions3 =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            vh.posterImageView, getString(R.string.detail_poster_transition_name));

            ActivityCompat.startActivity(this, intent, activityOptions2.toBundle());

            // startActivity(intent);
        }
    }
}
