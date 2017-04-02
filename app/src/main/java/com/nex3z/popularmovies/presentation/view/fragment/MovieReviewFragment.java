package com.nex3z.popularmovies.presentation.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.repository.review.ReviewRepository;
import com.nex3z.popularmovies.data.repository.review.ReviewRepositoryImpl;
import com.nex3z.popularmovies.domain.executor.JobExecutor;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.presentation.presenter.MovieReviewPresenter;
import com.nex3z.popularmovies.presentation.view.MovieReviewView;
import com.nex3z.popularmovies.presentation.view.UIThread;
import com.nex3z.popularmovies.presentation.view.adapter.ReviewAdapter;
import com.nex3z.popularmovies.presentation.view.misc.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieReviewFragment extends BaseFragment implements MovieReviewView {
    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();
    private static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.rv_review_list) RecyclerView mReviewRecyclerView;
    @BindView(R.id.pb_load_review) ProgressBar mProgressBar;
    @BindView(R.id.tv_no_review) TextView mTvNoReview;

    private Unbinder mUnbinder;
    private MovieReviewPresenter mPresenter;
    private ReviewAdapter mAdapter;
    private MovieModel mMovie;

    public MovieReviewFragment() {}

    public static MovieReviewFragment newInstance(Parcelable movie) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_MOVIE_INFO, movie);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_MOVIE_INFO)) {
                mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
            }
        }
        Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.destroy();
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
    public void renderReviews(List<ReviewModel> reviews) {
        if (reviews != null && reviews.size() != 0) {
            mAdapter.setReviews(reviews);
        } else {
            mTvNoReview.setVisibility(View.VISIBLE);
        }
    }

    private void initPresenter() {
        ReviewRepository reviewRepository = new ReviewRepositoryImpl(App.getRestClient());
        GetReviewUseCase useCase = new GetReviewUseCase(reviewRepository, new JobExecutor(), new UIThread());
        mPresenter = new MovieReviewPresenter(mMovie, useCase);
        mPresenter.setView(this);
        mPresenter.init();
    }

    private void initRecyclerView() {
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
