package com.nex3z.popularmovies.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.data.repository.movie.MovieRepositoryImpl;
import com.nex3z.popularmovies.domain.executor.JobExecutor;
import com.nex3z.popularmovies.domain.interactor.movie.DiscoverMovieUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieListPresenter;
import com.nex3z.popularmovies.presentation.view.MovieListView;
import com.nex3z.popularmovies.presentation.view.UIThread;
import com.nex3z.popularmovies.presentation.view.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.view.misc.EndlessRecyclerOnScrollListener;
import com.nex3z.popularmovies.presentation.view.misc.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieListFragment extends BaseFragment implements MovieListView {
    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();

    @BindView(R.id.rv_movie_list) RecyclerView mRvMovieList;
    @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.pb_load_movie) ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_message) TextView mTvEmptyMessage;

    private static OnItemSelectListener sDummyListener = (movie, vh) -> {};

    private Unbinder mUnbinder;
    private MovieListPresenter mPresenter;
    private MovieAdapter mMovieAdapter;
    private List<MovieModel> mMovies = new ArrayList<>();
    private EndlessRecyclerOnScrollListener mEndlessScroller;
    private OnItemSelectListener mListener = sDummyListener;

    public MovieListFragment() {}

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnItemSelectListener)) {
            throw new IllegalStateException("Activity must implement OnItemSelectListener.");
        }
        mListener = (OnItemSelectListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
    public void renderMovieList(Collection<MovieModel> movieModels) {
        Log.v(LOG_TAG, "renderMovieList(): movieModelCollection = " + movieModels.size());
        if (movieModels.isEmpty()) {
            mTvEmptyMessage.setVisibility(View.VISIBLE);
        } else {
            mTvEmptyMessage.setVisibility(View.GONE);
        }
        mMovieAdapter.setMovieCollection(movieModels);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderMovieList(Collection<MovieModel> movieModels, int start, int count) {
        Log.v(LOG_TAG, "renderMovieList(): movieModels = " + movieModels.size()
                + ", start = " + start + ", count = " + count);
        mMovieAdapter.notifyItemRangeInserted(start, count);
    }

    private void setupRecyclerView() {
        Log.v(LOG_TAG, "setupRecyclerView()");
        mMovieAdapter = new MovieAdapter(mMovies);
        mMovieAdapter.setOnPosterClickListener((position, viewHolder) -> {
        });
        mMovieAdapter.setOnFavouriteClickListener((position, vh) -> {
        });

        mRvMovieList.setAdapter(mMovieAdapter);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRvMovieList.setLayoutManager(mLayoutManager);
        mRvMovieList.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));
        mRvMovieList.setHasFixedSize(true);

        mEndlessScroller = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                Log.v(LOG_TAG, "onLoadMore(): page = " + page);
                mPresenter.loadMore(page);
            }
        };
        mRvMovieList.addOnScrollListener(mEndlessScroller);
    }

    private void init() {
        MovieRepository movieRepository = new MovieRepositoryImpl(App.getRestClient());
        DiscoverMovieUseCase useCase = new DiscoverMovieUseCase(movieRepository, new JobExecutor(), new UIThread());
        mPresenter = new MovieListPresenter(useCase);
        mPresenter.setView(this);
        mPresenter.initialize();
    }

    public interface OnItemSelectListener {
        void OnItemSelect(MovieModel movieModel, MovieAdapter.ViewHolder vh);
    }
}
