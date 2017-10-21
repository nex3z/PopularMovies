package com.nex3z.popularmovies.presentation.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BaseFragment;
import com.nex3z.popularmovies.presentation.base.HasPresenter;
import com.nex3z.popularmovies.presentation.misc.SpacingItemDecoration;
import com.nex3z.popularmovies.presentation.util.ViewUtil;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverMovieFragment extends BaseFragment
        implements DiscoverMovieView, HasPresenter<DiscoverMoviePresenter> {
    private static final String LOG_TAG = DiscoverMovieFragment.class.getSimpleName();

    @BindView(R.id.swipy_discover_movie_list) SwipyRefreshLayout mSwipyMovieList;
    @BindView(R.id.rv_discover_movie_list) RecyclerView mRvMovieList;

    private DiscoverMoviePresenter mPresenter;
    private MovieDetailNavigator mMovieDetailNavigator;
    private final MovieAdapter mMovieAdapter = new MovieAdapter();

    public DiscoverMovieFragment() {}

    public static DiscoverMovieFragment newInstance() {
        return new DiscoverMovieFragment();
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
    public DiscoverMoviePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoading() {
        mSwipyMovieList.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipyMovieList.setRefreshing(false);
    }

    @Override
    public void renderMovies(List<MovieModel> movies) {
        mMovieAdapter.setMovies(movies);
    }

    @Override
    public void notifyMovieInserted(int position, int count) {
        mMovieAdapter.notifyItemRangeInserted(position, count);
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
        mSwipyMovieList.setOnRefreshListener(direction -> {
            switch (direction) {
                case TOP:
                    mPresenter.refreshMovie();
                    break;
                case BOTTOM:
                    mPresenter.loadMoreMovie();
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
        mPresenter = new DiscoverMoviePresenter();
        mPresenter.setView(this);
    }

}
