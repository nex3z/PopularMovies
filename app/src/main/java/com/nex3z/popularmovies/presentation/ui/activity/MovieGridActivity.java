package com.nex3z.popularmovies.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieGridFragment;

public class MovieGridActivity extends AppCompatActivity implements MovieGridFragment.Callbacks {
    private static final String LOG_TAG = MovieGridActivity.class.getSimpleName();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (findViewById(R.id.movie_detail_container) != null) {
//            mTwoPane = true;
//        }
//        Log.v(LOG_TAG, "onCreate(): mTwoPane = " + mTwoPane);

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
    public void onItemSelected(MovieModel movieModel, MovieAdapter.ViewHolder vh) {
        Log.v(LOG_TAG, "onItemSelected(): movieModel = " + movieModel);
        if (mTwoPane) {
//            Bundle args = new Bundle();
//            args.putParcelable(MovieInfoFragment.DETAIL_URI, uri);
//
//            MovieInfoFragment fragment = new MovieInfoFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
//                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .putExtra(MovieDetailActivity.MOVIE_INFO, movieModel);

//            ActivityOptionsCompat activityOptions =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
//                            new Pair<View, String>(vh.posterImageView, getString(R.string.detail_poster_transition_name)));

//            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());

             startActivity(intent);
        }
    }
}
