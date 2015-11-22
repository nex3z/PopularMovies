package com.nex3z.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.ui.fragment.MovieGridFragment;


public class MovieGridActivity extends AppCompatActivity implements MovieGridFragment.Callbacks{

    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
        Log.v(LOG_TAG, "onCreate(): mTwoPane = " + mTwoPane);
    }

    @Override
    public void onItemSelected(Movie movie) {
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
            startActivity(intent);
        }
    }
}
