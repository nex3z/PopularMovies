package com.nex3z.popularmovies.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.entity.mapper.MovieEntityDataMapper;
import com.nex3z.popularmovies.data.executor.JobExecutor;
import com.nex3z.popularmovies.data.repository.MovieDataRepository;
import com.nex3z.popularmovies.data.repository.datasource.movie.MovieDataStoreFactory;
import com.nex3z.popularmovies.domain.interactor.movie.DeleteMovie;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.SaveMovie;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.mapper.MovieDataMapper;
import com.nex3z.popularmovies.domain.repository.MovieRepository;
import com.nex3z.popularmovies.presentation.UIThread;
import com.nex3z.popularmovies.presentation.mapper.MovieModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieListPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieGridView;
import com.nex3z.popularmovies.presentation.ui.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.ui.misc.EndlessRecyclerOnScrollListener;
import com.nex3z.popularmovies.presentation.ui.misc.SpacesItemDecoration;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieGridFragment extends Fragment implements MovieGridView {
    private static final String LOG_TAG = MovieGridFragment.class.getSimpleName();

    private static final String ARG_LIST_TYPE = "arg_list_type";
    public static final String DISCOVERY = "discovery";
    public static final String FAVOURITE = "favourite";

    private MovieAdapter mMovieAdapter;

    private static Callbacks sDummyCallbacks = (movie, vh) -> {};
    private Callbacks mCallbacks = sDummyCallbacks;

    private MovieListPresenter mPresenter;
    private EndlessRecyclerOnScrollListener mEndlessScroller;

    private String mType;

    @BindView(R.id.rv_movie_grid) RecyclerView mMovieRecyclerView;
    @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.pb_load_movie) ProgressBar mProgressBar;

    public interface Callbacks {
        void onItemSelected(MovieModel movieModel, MovieAdapter.ViewHolder vh);
    }

    public MovieGridFragment() {}

    public static MovieGridFragment newInstance(String type) {
        MovieGridFragment fragment = new MovieGridFragment();
        Bundle arguments = new Bundle();
        arguments.putString(MovieGridFragment.ARG_LIST_TYPE, type);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static MovieGridFragment newDiscoveryInstance() {
        return newInstance(DISCOVERY);
    }

    public static MovieGridFragment newFavouriteInstance() {
        return newInstance(FAVOURITE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_LIST_TYPE)) {
                mType = getArguments().getString(ARG_LIST_TYPE);
                Log.v(LOG_TAG, "onCreate(): mType = " + mType);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadMovies();
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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showRetry() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderMovieList(Collection<MovieModel> movieModelCollection) {
        Log.v(LOG_TAG, "renderMovieList(): movieModelCollection = " + movieModelCollection.size());
        mMovieAdapter.setMovieCollection(movieModelCollection);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderMovieList(Collection<MovieModel> movieModelCollection,
                                int start, int count) {
        Log.v(LOG_TAG, "renderMovieList(): movieModelCollection = " + movieModelCollection.size()
                + ", start = " + start + ", count = " + count);
        mMovieAdapter.notifyItemRangeInserted(start, count);
    }

    private void initialize() {
        MovieRepository repository = new MovieDataRepository(
                new MovieDataStoreFactory(), new MovieEntityDataMapper(), new MovieDataMapper());

        UseCase getMovieList;
        if (mType.equals(DISCOVERY)) {
            getMovieList = new GetMovieList(repository, new JobExecutor(), new UIThread());
        } else {
            getMovieList = new GetFavouriteMovieList(repository, new JobExecutor(), new UIThread());
        }

        UseCase saveMovie = new SaveMovie(repository, new JobExecutor(), new UIThread());
        UseCase deleteMovie = new DeleteMovie(repository, new JobExecutor(), new UIThread());

        mPresenter = new MovieListPresenter(getMovieList, saveMovie, deleteMovie,
                new MovieModelDataMapper());
        mPresenter.setView(this);

        setupRecyclerView();
        mSwipeLayout.setOnRefreshListener(() -> {
            mEndlessScroller.reset();
            mPresenter.refresh();
        });
    }

    private void loadMovies() {
        mPresenter.initialize();
    }

    private void setupRecyclerView() {
        Log.v(LOG_TAG, "setupRecyclerView()");
        mMovieAdapter = new MovieAdapter(mPresenter.getMovies());
        mMovieAdapter.setOnPosterClickListener((position, viewHolder) -> {
            Log.v(LOG_TAG, "onClick(): position = " + position);
            MovieModel movie = mPresenter.onMovieSelected(position);
            if (movie != null) {
                mCallbacks.onItemSelected(movie, viewHolder);
            }
        });
        mMovieAdapter.setOnFavouriteClickListener(new MovieAdapter.OnFavouriteClickListener() {
            @Override
            public void onClick(int position, MovieAdapter.ViewHolder vh) {
                Log.v(LOG_TAG, "onClick(): vh.isFavourite() = " + vh.isFavourite());
                if (vh.isFavourite()) {
                    mPresenter.addToFavourite(position);
                } else {
                    mPresenter.removeFromFavourite(position);
                }
            }
        });

        mMovieRecyclerView.setAdapter(mMovieAdapter);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mMovieRecyclerView.setLayoutManager(mLayoutManager);
        mMovieRecyclerView.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));
        mMovieRecyclerView.setHasFixedSize(true);

        mEndlessScroller = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                Log.v(LOG_TAG, "onLoadMore(): page = " + page);
                if (mType.equals(DISCOVERY)) {
                    mPresenter.loadMore(page);
                }
            }
        };

        mMovieRecyclerView.addOnScrollListener(mEndlessScroller);
    }

}
