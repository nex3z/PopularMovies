package com.nex3z.popularmovies.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.provider.MovieContract;
import com.nex3z.popularmovies.data.rest.service.MovieService;
import com.nex3z.popularmovies.ui.adapter.MovieCursorAdapter;
import com.nex3z.popularmovies.ui.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = FavouriteFragment.class.getSimpleName();

    private MovieCursorAdapter mPosterAdapter;
    private GridLayoutManager mLayoutManager;
    private List<Movie> mMovies = new ArrayList<Movie>();
    private int mPage = 1;
    private String mSortBy = MovieService.SORT_BY_POPULARITY_DESC;
    private MovieGridFragment.Callbacks mCallbacks = sDummyCallbacks;
    private static final int MOVIE_LOADER = 0;

    @Bind(R.id.movie_grid) RecyclerView mMovieRecyclerView;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeLayout;

    private static MovieGridFragment.Callbacks sDummyCallbacks = (movie, vh) -> {};

    public FavouriteFragment() { }

    public static MovieGridFragment newInstance(String param1, String param2) {
        MovieGridFragment fragment = new MovieGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        ButterKnife.bind(this, rootView);

        setupRecyclerView(mMovieRecyclerView);

        mSwipeLayout.setOnRefreshListener(() -> fetchMovies());

        mPosterAdapter = new MovieCursorAdapter();
        mPosterAdapter.setOnItemClickListener((position, viewHolder) -> {
            Log.v(LOG_TAG, "onItemClick(): position = " + position);
            Movie movie = mPosterAdapter.getMovie(position);
            Log.v(LOG_TAG, "onItemClick(): movie = " + movie);
            if (movie != null) {
                mCallbacks.onItemSelected(movie, viewHolder);
            }
        });
        // mMovieRecyclerView.setAdapter(new ScaleInAnimationAdapter(mPosterAdapter));
        mMovieRecyclerView.setAdapter(mPosterAdapter);
//        fetchMovies();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.v(LOG_TAG, "onAttach()");
        if (!(context instanceof MovieGridFragment.Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (MovieGridFragment.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(LOG_TAG, "onDetach()");
        mCallbacks = sDummyCallbacks;
    }

    public void fetchMovies() {
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        Log.v(LOG_TAG, "onCreateLoader(): uri = " + uri);

        String sortOrder = MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
//        if (args != null) {
//            String sortBy = args.getString("sort_by");
//            if (sortBy.equals(getActivity().getString(R.string.pref_sort_popularity))) {
//                sortOrder = MovieContract.MovieEntry.COLUMN_POPULARITY + " DESC";
//            } else if (sortBy.equals(getActivity().getString(R.string.pref_sort_release_date))) {
//                sortOrder = MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " DESC";
//            }
//        }

        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "onLoadFinished(): data = " + data);
        mPosterAdapter.swapCursor(data);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(LOG_TAG, "onLoaderReset()");
        mPosterAdapter.swapCursor(null);
    }
}
