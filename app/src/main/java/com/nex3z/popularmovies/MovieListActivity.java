package com.nex3z.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nex3z.popularmovies.util.Utility;


public class MovieListActivity extends AppCompatActivity
        implements MovieListFragment.Callbacks {

    private static final String LOG_TAG = MovieListActivity.class.getSimpleName();

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private String mSortBy;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
//            ((MovieListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.movie_list))
//                    .setActivateOnItemClick(true);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String sortBy = Utility.getPreferredSortOrder(this);
        Log.v(LOG_TAG, "onResume(): sortBy = " + sortBy + " mSortBy = " + mSortBy);

        if (sortBy != null && !sortBy.equals(mSortBy)) {
            MovieListFragment movieListFragment =
                    (MovieListFragment)getSupportFragmentManager().findFragmentById(R.id.movie_list);
            if (movieListFragment != null) {
                Log.v(LOG_TAG, "onResume(): Found DiscoveryFragment.");
                movieListFragment.onSortPrefChanged(sortBy);
            }
            mSortBy = sortBy;
        }
    }

    @Override
    public void onItemSelected(Uri uri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(MovieDetailFragment.DETAIL_URI, uri);

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .setData(uri);
            startActivity(intent);
        }
    }
}
