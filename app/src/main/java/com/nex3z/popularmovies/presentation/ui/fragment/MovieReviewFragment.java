package com.nex3z.popularmovies.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.internal.di.component.MovieDetailComponent;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.presenter.MovieReviewPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieReviewView;
import com.nex3z.popularmovies.presentation.ui.adapter.ReviewAdapter;
import com.nex3z.popularmovies.presentation.ui.misc.DividerItemDecoration;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieReviewFragment extends BaseFragment implements MovieReviewView {
    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.rv_review_list) RecyclerView mReviewRecyclerView;
    @BindView(R.id.pb_load_review) ProgressBar mProgressBar;
    @BindView(R.id.tv_no_review) TextView mTvNoReview;

    @Inject MovieReviewPresenter mPresenter;

    private ReviewAdapter mAdapter;
    private Unbinder mUnbinder;

    public MovieReviewFragment() {}

    public static MovieReviewFragment newInstance(Parcelable parcelable) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieVideoFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected boolean onInjectView() throws IllegalStateException {
        getComponent(MovieDetailComponent.class).inject(this);
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
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
    public void renderReviews(Collection<ReviewModel> reviewModelCollection) {
        if (reviewModelCollection != null && reviewModelCollection.size() != 0) {
            mAdapter.setReviewsCollection(reviewModelCollection);
        } else {
            mTvNoReview.setVisibility(View.VISIBLE);
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
