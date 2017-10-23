package com.nex3z.popularmovies.presentation.detail.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.presentation.base.BaseFragment;
import com.nex3z.popularmovies.presentation.base.HasPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieReviewFragment extends BaseFragment
        implements MovieReviewView, HasPresenter<MovieReviewPresenter> {
    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.rv_movie_review_list) RecyclerView mRvReviewList;
    @BindView(R.id.pb_movie_review_loading) ProgressBar mPbLoading;

    private MovieReviewPresenter mPresenter;
    private MovieModel mMovie;
    private final ReviewAdapter mAdapter = new ReviewAdapter();
    private Unbinder mUnbinder;

    public MovieReviewFragment() {}

    public static MovieReviewFragment newInstance(MovieModel movie) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
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
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public MovieReviewPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void renderReviews(List<ReviewModel> reviews) {
        mAdapter.setReviews(reviews);
    }

    private void init() {
        initView();
        initPresenter();
        mPresenter.init();
    }

    private void initView() {
        mRvReviewList.setAdapter(mAdapter);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvReviewList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        mRvReviewList.addItemDecoration(dividerItemDecoration);
    }

    private void initPresenter() {
        mPresenter = new MovieReviewPresenter(mMovie);
        mPresenter.setView(this);
    }

}
