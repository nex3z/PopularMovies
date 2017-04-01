package com.nex3z.popularmovies.presentation.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.view.fragment.MovieListFragment;

public class MovieListActivity extends BaseActivity implements
        MovieListFragment.OnItemSelectListener {
    private static final String LOG_TAG = MovieListActivity.class.getSimpleName();

    private Fragment mDiscoveryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }
}
