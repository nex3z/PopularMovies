package com.nex3z.popularmovies;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.data.MovieContract;
import com.nex3z.popularmovies.util.Utility;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    static final String DETAIL_URI = "URI";
    private static final String MOVIE_SHARE_HASHTAG = " #PopularMoviesApp";
    private static final int DETAIL_LOADER = 0;
    private Uri mUri;
    private String mMovie;
    private ShareActionProvider mShareActionProvider;

    private static final String[] DETAIL_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_ID,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW
    };

    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_MOVIE_ID = 1;
    public static final int COL_MOVIE_BACKDROP_PATH = 2;
    public static final int COL_MOVIE_TITLE = 3;
    public static final int COL_MOVIE_RELEASE_DATE = 4;
    public static final int COL_MOVIE_VOTE_AVERAGE = 5;
    public static final int COL_MOVIE_OVERVIEW = 6;

    CollapsingToolbarLayout mAppBarLayout;
    ImageView mBackdropView;
    private TextView mReleaseDateView;
    private TextView mRateView;
    private TextView mOverviewView;

    public MovieDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(MovieDetailFragment.DETAIL_URI);
            Log.v(LOG_TAG, "onCreate(): mUri = " + mUri);

        }

        mAppBarLayout = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        mBackdropView = (ImageView)getActivity().findViewById(R.id.detail_backdrop_image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mReleaseDateView = (TextView)rootView.findViewById(R.id.detail_release_date_textview);
        mRateView = (TextView)rootView.findViewById(R.id.detail_rate_textview);
        mOverviewView = (TextView)rootView.findViewById(R.id.detail_overview_textview);

        Log.v(LOG_TAG, "onCreateView(): mBackdropView = " + mBackdropView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mMovie != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            String imageUrl = Utility.convertPosterImagePath(data.getString(COL_MOVIE_BACKDROP_PATH));
            Log.v(LOG_TAG, "onLoadFinished(): imageUrl = " + imageUrl);
            Log.v(LOG_TAG, "mBackdropView =" + mBackdropView);
            Picasso.with(getActivity()).load(imageUrl).into(mBackdropView);

            String title = data.getString(COL_MOVIE_TITLE);
            mAppBarLayout.setTitle(title);

            String releaseDate = data.getString(COL_MOVIE_RELEASE_DATE);
            Log.v(LOG_TAG, "onLoadFinished(): releaseDate = " + releaseDate);
            mReleaseDateView.setText(getString(R.string.release_date) + releaseDate);
            mRateView.setText(getString(R.string.rate) + data.getString(COL_MOVIE_VOTE_AVERAGE) + "/10");
            String overview = data.getString(COL_MOVIE_OVERVIEW);
            mOverviewView.setText(overview);

            mMovie = String.format("%s - %s", title, overview);

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovie + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }
}
