package com.nex3z.popularmovies.presentation.detail.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.base.BaseFragment;
import com.nex3z.popularmovies.presentation.base.HasPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieVideoFragment extends BaseFragment
        implements MovieVideoView, HasPresenter<MovieVideoPresenter> {
    private static final String LOG_TAG = MovieVideoFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.rv_movie_video_list) RecyclerView mRvVideoList;
    @BindView(R.id.pb_movie_video_loading) ProgressBar mPbLoading;
    @BindView(R.id.tv_movie_video_empty_message) TextView mTvEmptyMessage;

    private MovieVideoPresenter mPresenter;
    private MovieModel mMovie;
    private final VideoAdapter mAdapter = new VideoAdapter();
    private Unbinder mUnbinder;

    public MovieVideoFragment() {}

    public static MovieVideoFragment newInstance(MovieModel movie) {
        MovieVideoFragment fragment = new MovieVideoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_movie_video, container, false);
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
    public MovieVideoPresenter getPresenter() {
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
    public void renderVideos(List<VideoModel> videos) {
        mAdapter.setVideos(videos);
        mTvEmptyMessage.setVisibility((videos != null && !videos.isEmpty()) ?
                View.GONE : View.VISIBLE);
    }

    @Override
    public void playVideo(VideoModel video) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(video.getVideoUrl());
        intent.setData(uri);
        startActivity(intent);
    }

    private void init() {
        initView();
        initPresenter();
        mPresenter.init();
    }

    private void initView() {
        mAdapter.setOnVideoClickListener(position -> mPresenter.onVideoClick(position));
        mRvVideoList.setAdapter(mAdapter);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvVideoList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        mRvVideoList.addItemDecoration(dividerItemDecoration);
    }

    private void initPresenter() {
        mPresenter = new MovieVideoPresenter(mMovie);
        mPresenter.setView(this);
    }

}
