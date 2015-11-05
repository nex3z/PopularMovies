package com.nex3z.popularmovies;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.nex3z.popularmovies.data.MovieContract;
import com.nex3z.popularmovies.service.MovieService;
import com.nex3z.popularmovies.util.Utility;


public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    private PosterAdapter mPosterAdapter;
    private GridView mGridView;
    private int mPosition = GridView.INVALID_POSITION;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_ID
    };

    static final int COL_MOVIE_ID = 0;
    static final int COL_MOVIE_TITLE = 1;
    static final int COL_MOVIE_POSTER_PATH = 2;
    static final int COL_MOVIE_MOVIE_ID = 3;

    private static final int MOVIE_LOADER = 0;

    public interface Callbacks {
        public void onItemSelected(Uri uri);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Uri uri) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // TODO: replace with a real list adapter.
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                DummyContent.ITEMS));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPosterAdapter = new PosterAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.posters_grid);
        mGridView.setAdapter(mPosterAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                Log.v(LOG_TAG, "onItemClick(): position = " + String.valueOf(position) + ", cursor = " + cursor);
                if (cursor != null) {
                    mCallbacks.onItemSelected(MovieContract.MovieEntry.buildMovieUri(
                            cursor.getLong(COL_MOVIE_MOVIE_ID)
                    ));
                }
                mPosition = position;
            }
        });

        updateMovies();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        String sortBy = Utility.getPreferredSortOrder(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("sort_by", sortBy);
        getLoaderManager().restartLoader(MOVIE_LOADER, bundle, this);

        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        Log.v(LOG_TAG, "onActivityCreated(): Loader initiated.");

        super.onActivityCreated(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.discoveryfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Log.v(LOG_TAG, "onOptionsItemSelected(): Selected id = action_refresh");
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMovies() {
        Log.v(LOG_TAG, "updateMovies()");
        MovieService.startActionFetchNewMovies(getActivity(), "unused");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        Log.v(LOG_TAG, "onCreateLoader(): uri = " + uri);

        String sortOrder = MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
        if (args != null) {
            String sortBy = args.getString("sort_by");
            if (sortBy.equals(getActivity().getString(R.string.pref_sort_popularity))) {
                sortOrder = MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
            } else if (sortBy.equals(getActivity().getString(R.string.pref_sort_release_date))) {
                sortOrder = MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " DESC";
            }
        }

        return new CursorLoader(getActivity(),
                uri,
                MOVIE_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "onLoadFinished(): data = " + data);
        mPosterAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(LOG_TAG, "onLoaderReset()");
        mPosterAdapter.swapCursor(null);
    }

    void onSortPrefChanged(String sortBy) {
        updateMovies();
        Bundle bundle = new Bundle();
        bundle.putString("sort_by", sortBy);
        getLoaderManager().restartLoader(MOVIE_LOADER, bundle, this);
    }



    //    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Restore the previously serialized activated item position.
//        if (savedInstanceState != null
//                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
//            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

//    @Override
//    public void onListItemClick(ListView listView, View view, int position, long id) {
//        super.onListItemClick(listView, view, position, id);
//
//        // Notify the active callbacks interface (the activity, if the
//        // fragment is attached to one) that an item has been selected.
//        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
//    public void setActivateOnItemClick(boolean activateOnItemClick) {
//        // When setting CHOICE_MODE_SINGLE, ListView will automatically
//        // give items the 'activated' state when touched.
//        getListView().setChoiceMode(activateOnItemClick
//                ? ListView.CHOICE_MODE_SINGLE
//                : ListView.CHOICE_MODE_NONE);
//    }

//    private void setActivatedPosition(int position) {
//        if (position == ListView.INVALID_POSITION) {
//            getListView().setItemChecked(mActivatedPosition, false);
//        } else {
//            getListView().setItemChecked(position, true);
//        }
//
//        mActivatedPosition = position;
//    }
}
