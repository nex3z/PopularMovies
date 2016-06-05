package com.nex3z.popularmovies.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.entity.mapper.ReviewEntityDataMapper;
import com.nex3z.popularmovies.data.executor.JobExecutor;
import com.nex3z.popularmovies.data.repository.ReviewDataRepository;
import com.nex3z.popularmovies.data.repository.datasource.ReviewDataStoreFactory;
import com.nex3z.popularmovies.domain.interactor.GetReviewList;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;
import com.nex3z.popularmovies.presentation.UIThread;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.presenter.MovieReviewPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieReviewView;
import com.nex3z.popularmovies.presentation.ui.adapter.ReviewAdapter;
import com.nex3z.popularmovies.presentation.ui.misc.DividerItemDecoration;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewFragment extends Fragment implements MovieReviewView {
    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.rv_review_list) RecyclerView mReviewRecyclerView;
    @BindView(R.id.pb_load_review) ProgressBar mProgressBar;

    private MovieModel mMovie;
    private ReviewAdapter mAdapter;
    private MovieReviewPresenter mPresenter;

    public MovieReviewFragment() {}

    public static MovieReviewFragment newInstance(Parcelable parcelable) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieVideoFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_MOVIE_INFO)) {
                mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
                Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadReviews();
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
    public void renderReviews(Collection<ReviewModel> reviewModelCollection) {
        if (reviewModelCollection != null) {
            mAdapter.setReviewsCollection(reviewModelCollection);
        }
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {}

    @Override
    public void hideRetry() {}

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initialize() {
        ReviewRepository reviewRepo = new ReviewDataRepository(
                new ReviewDataStoreFactory(), new ReviewEntityDataMapper());
        UseCase getVideoList = new GetReviewList(reviewRepo, new JobExecutor(), new UIThread());
        mPresenter = new MovieReviewPresenter(mMovie, getVideoList, new ReviewModelDataMapper());
        mPresenter.setView(this);

        setupRecyclerView();
    }

    private void loadReviews() {
        mPresenter.initialize();
    }

    private void setupRecyclerView() {
        mAdapter = new ReviewAdapter();
        mReviewRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mReviewRecyclerView.setLayoutManager(layoutManager);

        mReviewRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                getContext(), DividerItemDecoration.VERTICAL_LIST);
        mReviewRecyclerView.addItemDecoration(itemDecoration);
    }
}
