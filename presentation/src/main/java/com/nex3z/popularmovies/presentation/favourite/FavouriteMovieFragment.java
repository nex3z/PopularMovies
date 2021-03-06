package com.nex3z.popularmovies.presentation.favourite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BaseFragment;
import com.nex3z.popularmovies.presentation.base.HasPresenter;
import com.nex3z.popularmovies.presentation.discover.MovieAdapter;
import com.nex3z.popularmovies.presentation.discover.MovieDetailNavigator;
import com.nex3z.popularmovies.presentation.misc.SpacingItemDecoration;
import com.nex3z.popularmovies.presentation.util.ViewUtil;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteMovieFragment extends BaseFragment
        implements FavouriteMovieView, HasPresenter<FavouriteMoviePresenter> {
    private static final String LOG_TAG = FavouriteMovieFragment.class.getSimpleName();

    @BindView(R.id.swipy_discover_movie_list) SwipyRefreshLayout mSwipyMovieList;
    @BindView(R.id.rv_discover_movie_list) RecyclerView mRvMovieList;
    @BindView(R.id.pb_discover_movie_loading) ProgressBar mPbLoading;

    private FavouriteMoviePresenter mPresenter;
    private MovieDetailNavigator mMovieDetailNavigator;
    private final MovieAdapter mMovieAdapter = new MovieAdapter();

    public FavouriteMovieFragment() {}

    public static FavouriteMovieFragment newInstance() {
        return new FavouriteMovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_movie, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieDetailNavigator) {
            mMovieDetailNavigator = (MovieDetailNavigator) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MovieDetailNavigator");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMovieDetailNavigator = null;
    }

    @Override
    public FavouriteMoviePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {
        mSwipyMovieList.setRefreshing(false);
    }

    @Override
    public void renderMovies(List<MovieModel> movies) {
        mMovieAdapter.setMovies(movies);
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void notifyMovieChanged(int position) {
        mMovieAdapter.notifyItemChanged(position);
    }

    @Override
    public void renderMovieDetail(MovieModel movie) {
        mMovieDetailNavigator.navigateToDetail(movie);
    }

    private void init() {
        initView();
        initPresenter();
        mPresenter.init();
    }

    private void initView() {
        mSwipyMovieList.setDirection(SwipyRefreshLayoutDirection.TOP);
        mSwipyMovieList.setOnRefreshListener(direction -> {
            switch (direction) {
                case TOP:
                    mPresenter.refreshMovie();
                    break;
                default:
                    break;
            }
        });

        mMovieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int position) {
                mPresenter.onMovieClicked(position);
            }
            @Override
            public void onFavouriteClick(int position) {
                mPresenter.toggleFavourite(position);
            }
        });
        mRvMovieList.setAdapter(mMovieAdapter);

        GridLayoutManager layoutManager =
                new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        mRvMovieList.setLayoutManager(layoutManager);

        SpacingItemDecoration decoration = new SpacingItemDecoration((int) ViewUtil.dpToPx(4));
        mRvMovieList.addItemDecoration(decoration);
    }

    private void initPresenter() {
        mPresenter = new FavouriteMoviePresenter();
        mPresenter.setView(this);
    }

}
