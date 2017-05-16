package com.nex3z.popularmovies.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.movie.DiscoverMovieUseCase;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieUseCase;
import com.nex3z.popularmovies.domain.interactor.movie.SetFavouriteUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieListPresenter;
import com.nex3z.popularmovies.presentation.view.MovieListView;
import com.nex3z.popularmovies.presentation.view.UIThread;
import com.nex3z.popularmovies.presentation.view.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.view.misc.SpacesItemDecoration;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieListFragment extends BaseFragment implements MovieListView {
    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();
    private static final String ARG_LIST_TYPE = "arg_list_type";
    public static final String DISCOVERY = "discovery";
    public static final String FAVOURITE = "favourite";

    @BindView(R.id.rv_movie_list) RecyclerView mRvMovieList;
    @BindView(R.id.swipe_container) SwipyRefreshLayout mSwipeLayout;
    @BindView(R.id.pb_load_movie) ProgressBar mProgressBar;
    @BindView(R.id.tv_empty_message) TextView mTvEmptyMessage;

    private static OnItemSelectListener sDummyListener = (movie, vh) -> {};

    private String mType;
    private Unbinder mUnbinder;
    private MovieListPresenter mPresenter;
    private MovieAdapter mMovieAdapter;
    private List<MovieModel> mMovies = new ArrayList<>();
    private MovieAdapter.ViewHolder mViewHolder;
    private OnItemSelectListener mListener = sDummyListener;

    public MovieListFragment() {}

    public static MovieListFragment newInstance(String type) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_LIST_TYPE, type);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static MovieListFragment newDiscoveryInstance() {
        return newInstance(DISCOVERY);
    }

    public static MovieListFragment newFavouriteInstance() {
        return newInstance(FAVOURITE);
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
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMovieList();
        initPresenter();
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
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
    public void renderMovies(List<MovieModel> movieModels) {
        Log.v(LOG_TAG, "renderMovies(): movieModelCollection = " + movieModels.size());
        if (movieModels.isEmpty()) {
            mTvEmptyMessage.setVisibility(View.VISIBLE);
        } else {
            mTvEmptyMessage.setVisibility(View.GONE);
        }
        mMovieAdapter.setMovies(movieModels);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderMovies(List<MovieModel> movieModels, int start, int count) {
        Log.v(LOG_TAG, "renderMovies(): movieModels = " + movieModels.size()
                + ", start = " + start + ", count = " + count);
        mMovieAdapter.notifyItemRangeInserted(start, count);
    }

    @Override
    public void updateMovie(int position) {
        mMovieAdapter.notifyItemChanged(position);
    }

    @Override
    public void showDetail(MovieModel movie) {
        mListener.OnItemSelect(movie, mViewHolder);
    }

    private void initMovieList() {
        mMovieAdapter = new MovieAdapter(mMovies);
        mMovieAdapter.setOnPosterClickListener((position, viewHolder) -> {
            mViewHolder = viewHolder;
            mPresenter.onMovieSelect(position);
        });
        mMovieAdapter.setOnFavouriteClickListener((position, vh) -> {
            mPresenter.swapFavourite(position);
        });
        mRvMovieList.setAdapter(mMovieAdapter);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRvMovieList.setLayoutManager(mLayoutManager);
        mRvMovieList.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));
        mRvMovieList.setHasFixedSize(true);

        mSwipeLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case TOP:
                    mPresenter.refresh();
                    break;
                case BOTTOM:
                    mPresenter.loadMore();
                    break;
                default:
                    break;
            }
        });
    }

    private void initPresenter() {
        MovieRepository movieRepository = new MovieRepositoryImpl(App.getRestClient());
        SetFavouriteUseCase addToFavouriteUseCase = new SetFavouriteUseCase(movieRepository, new JobExecutor(), new UIThread());
        UseCase getMovieListUseCase;
        if (mType.equals(DISCOVERY)) {
            getMovieListUseCase = new DiscoverMovieUseCase(movieRepository, new JobExecutor(), new UIThread());
        } else {
            getMovieListUseCase = new GetFavouriteMovieUseCase(movieRepository, new JobExecutor(), new UIThread());

        }
        mPresenter = new MovieListPresenter(getMovieListUseCase, addToFavouriteUseCase);
        mPresenter.setView(this);
        mPresenter.init();
    }

    public interface OnItemSelectListener {
        void OnItemSelect(MovieModel movieModel, MovieAdapter.ViewHolder vh);
    }
}
