package com.nex3z.popularmovies.presentation.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.DiscoverMoviesUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.app.App;
import com.nex3z.popularmovies.presentation.misc.SpacingItemDecoration;
import com.nex3z.popularmovies.presentation.util.ViewUtil;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverMovieFragment extends Fragment {
    private static final String LOG_TAG = DiscoverMovieFragment.class.getSimpleName();
    private static final int FIRST_PAGE = 1;

    @BindView(R.id.swipy_discover_movie_list) SwipyRefreshLayout mSwipyMovieList;
    @BindView(R.id.rv_discover_movie_list) RecyclerView mRvMovieList;

    private DiscoverMoviesUseCase mDiscoverMoviesUseCase;
    private OnMovieClickListener mListener;
    private final List<MovieModel> mMovies = new ArrayList<>();
    private final MovieAdapter mMovieAdapter = new MovieAdapter();
    private int mPage = FIRST_PAGE;

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
        if (context instanceof OnMovieClickListener) {
            mListener = (OnMovieClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieClickListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDiscoverMoviesUseCase.dispose();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void init() {
        mDiscoverMoviesUseCase = App.getPopMovieService().create(DiscoverMoviesUseCase.class);
        initView();
        fetchMovies(FIRST_PAGE);
    }

    private void initView() {
        mSwipyMovieList.setOnRefreshListener(direction -> {
            switch (direction) {
                case TOP:
                    fetchMovies(FIRST_PAGE);
                    break;
                case BOTTOM:
                    fetchMovies(mPage + 1);
                    break;
                default:
                    break;
            }
        });

        mMovieAdapter.setMovies(mMovies);
        mMovieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int position) {

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

    private void fetchMovies(int page) {
        mDiscoverMoviesUseCase.execute(new MovieObserver(page),
                new DiscoverMovieParams.Builder()
                        .sortBy(DiscoverMovieParams.SORT_BY_POPULARITY_DESC)
                        .page(page)
                        .build()
        );
    }

    public interface OnMovieClickListener {
        void onMovieClick(MovieModel movie);
    }

    private class MovieObserver extends DefaultObserver<List<MovieModel>> {
        private final int mPage;
        MovieObserver(int page) {
            mPage = page;
        }

        @Override
        public void onNext(List<MovieModel> movies) {
            super.onNext(movies);
            Log.v(LOG_TAG, "onNext(): mPage = " + mPage + ", size = " + movies.size());

            if (mPage == FIRST_PAGE) {
                mMovies.clear();
                mMovies.addAll(movies);
                mMovieAdapter.notifyDataSetChanged();
            } else {
                int start = mMovies.size();
                mMovies.addAll(movies);
                mMovieAdapter.notifyItemRangeInserted(start, movies.size());
            }
            mSwipyMovieList.setRefreshing(false);
            DiscoverMovieFragment.this.mPage = mPage;
        }

        @Override
        public void onError(Throwable throwable) {
            super.onError(throwable);
            Log.e(LOG_TAG, "MovieObserver()", throwable);
        }
    }
}
