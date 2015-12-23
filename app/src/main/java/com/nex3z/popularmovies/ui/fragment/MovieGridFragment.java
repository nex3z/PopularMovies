package com.nex3z.popularmovies.ui.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.rest.model.MovieResponse;
import com.nex3z.popularmovies.data.rest.service.MovieService;
import com.nex3z.popularmovies.ui.adapter.AbstractMovieAdapter;
import com.nex3z.popularmovies.ui.adapter.MovieAdapter;
import com.nex3z.popularmovies.ui.listener.EndlessRecyclerOnScrollListener;
import com.nex3z.popularmovies.ui.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieGridFragment extends Fragment {

    private static final String LOG_TAG = MovieGridFragment.class.getSimpleName();

    private MovieAdapter mPosterAdapter;
    private GridLayoutManager mLayoutManager;
    private List<Movie> mMovies = new ArrayList<Movie>();
    private int mPage = 1;
    private String mSortBy = MovieService.SORT_BY_POPULARITY_DESC;
    private Callbacks mCallbacks = sDummyCallbacks;

    @Bind(R.id.movie_grid) RecyclerView mMovieRecyclerView;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.grid_progressbar) ProgressBar mProgressBar;
    @BindColor(R.color.color_primary) int mColorPrimary;

    public interface Callbacks {
        void onItemSelected(Movie movie, AbstractMovieAdapter.ViewHolder vh);
    }

    private static Callbacks sDummyCallbacks = (movie, vh) -> {};

    public MovieGridFragment() { }

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

        mSwipeLayout.setOnRefreshListener(() -> fetchMovies(mSortBy, mPage));

        Log.v(LOG_TAG, "mProgressBar = " + mProgressBar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(mColorPrimary, PorterDuff.Mode.MULTIPLY);

        mPosterAdapter = new MovieAdapter(mMovies);
        mPosterAdapter.setOnItemClickListener((position, viewHolder) -> {
            Log.v(LOG_TAG, "onItemClick(): position = " + position);
            Movie movie = mMovies.get(position);
            if (movie != null) {
                mCallbacks.onItemSelected(movie, viewHolder);
            }
        });
        mMovieRecyclerView.setAdapter(new ScaleInAnimationAdapter(mPosterAdapter));
        fetchMovies(mSortBy, mPage);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.v(LOG_TAG, "onAttach()");
        if (!(context instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(LOG_TAG, "onDetach()");
        mCallbacks = sDummyCallbacks;
    }

    public void fetchMovies(String sortBy, int page) {
        Log.v(LOG_TAG, "fetchMovies(): sortBy = " + sortBy + ", page = " + page);
        MovieService service = App.getRestClient().getMovieService();
        service.getMovies(sortBy, page)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processMovieResponse(response),
                        throwable -> processThrowable(throwable)
                );
    }

    private void processMovieResponse(MovieResponse response) {
        List<Movie> movies = response.getMovies();
        Log.v(LOG_TAG, "processMovieResponse(): movies size = " + movies.size());
        mMovies.addAll(movies);
        Log.v(LOG_TAG, "processMovieResponse(): mMovies size = " + mMovies.size());
        for(Movie movie : movies) {
            Log.v(LOG_TAG, "processMovieResponse(): movies id = " + movie.getId() + ", title = " + movie.getTitle());
        }
        mPosterAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);

        getActivity().supportStartPostponedEnterTransition();
    }

    private void processThrowable(Throwable throwable) {
        Snackbar.make(
                mMovieRecyclerView,
                throwable.getLocalizedMessage(),
                Snackbar.LENGTH_LONG
        ).show();
        mSwipeLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                fetchMovies(mSortBy, ++mPage);
                Log.v(LOG_TAG, "onScrolled(): mMovies updated, size = " + mMovies.size());
            }
        });
    }
}
