package com.nex3z.popularmovies.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.data.repository.movie.MovieRepositoryImpl;
import com.nex3z.popularmovies.domain.executor.JobExecutor;
import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.movie.DiscoverMovieUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.UIThread;
import com.nex3z.popularmovies.presentation.view.adapter.MovieAdapter;
import com.nex3z.popularmovies.presentation.view.misc.EndlessRecyclerOnScrollListener;
import com.nex3z.popularmovies.presentation.view.misc.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieListFragment extends BaseFragment {
    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();

    @BindView(R.id.rv_movie_list)
    RecyclerView mRvMovieList;

    private Unbinder mUnbinder;
    private MovieAdapter mMovieAdapter;
    private EndlessRecyclerOnScrollListener mEndlessScroller;
    private List<MovieModel> mMovies = new ArrayList<>();

    public MovieListFragment() {}

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        init();
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
    }

    private void init() {
        MovieRepository movieRepository = new MovieRepositoryImpl(App.getRestClient());
        DiscoverMovieUseCase useCase = new DiscoverMovieUseCase(movieRepository, new JobExecutor(), new UIThread());
        useCase.execute(new MovieObserver(), DiscoverMovieUseCase.Params.forPage(
                DiscoverMovieUseCase.Params.SORT_BY_POPULARITY_DESC, 1));
    }

    private class MovieObserver extends DefaultObserver<List<MovieModel>> {
        @Override
        public void onNext(List<MovieModel> movieModels) {
            Log.v(LOG_TAG, "onNext(): movieModels = " + movieModels.size());
            mMovies.addAll(movieModels);
            mMovieAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Throwable exception) {
            exception.printStackTrace();
        }
    }
}
