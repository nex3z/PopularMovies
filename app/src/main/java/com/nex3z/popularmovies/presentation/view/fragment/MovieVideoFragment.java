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
import com.nex3z.popularmovies.data.repository.video.VideoRepository;
import com.nex3z.popularmovies.data.repository.video.VideoRepositoryImpl;
import com.nex3z.popularmovies.domain.executor.JobExecutor;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.presenter.MovieVideoPresenter;
import com.nex3z.popularmovies.presentation.view.MovieVideoView;
import com.nex3z.popularmovies.presentation.view.UIThread;
import com.nex3z.popularmovies.presentation.view.adapter.VideoAdapter;
import com.nex3z.popularmovies.presentation.view.misc.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieVideoFragment extends BaseFragment implements MovieVideoView {
    private static final String LOG_TAG = MovieVideoFragment.class.getSimpleName();
    private static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.rv_video_list) RecyclerView mVideoRecyclerView;
    @BindView(R.id.pb_load_video) ProgressBar mProgressBar;
    @BindView(R.id.tv_no_video) TextView mTvNoVideo;

    private Unbinder mUnbinder;
    private MovieVideoPresenter mPresenter;
    private VideoAdapter mAdapter;
    private MovieModel mMovie;

    public MovieVideoFragment() {}

    public static MovieVideoFragment newInstance(Parcelable movie) {
        MovieVideoFragment fragment = new MovieVideoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_movie_video, container, false);
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
    }

    @Override
    public void renderVideos(List<VideoModel> videos) {
        if (videos != null && videos.size() != 0) {
            mAdapter.setVideos(videos);
        } else {
            mTvNoVideo.setVisibility(View.VISIBLE);
        }
    }

    private void initPresenter() {
        VideoRepository videoRepository = new VideoRepositoryImpl(App.getRestClient());
        GetVideoUseCase useCase = new GetVideoUseCase(videoRepository, new JobExecutor(), new UIThread());
        mPresenter = new MovieVideoPresenter(mMovie, useCase);
        mPresenter.setView(this);
        mPresenter.init();
    }

    private void initRecyclerView() {
        mAdapter = new VideoAdapter();
        mAdapter.setOnItemClickListener((position, viewHolder) -> {
            Log.v(LOG_TAG, "onClick(): position = " + position);
            mPresenter.playVideo(position);
        });
        mVideoRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mVideoRecyclerView.setLayoutManager(layoutManager);

        mVideoRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                getContext(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.addItemDecoration(itemDecoration);
    }
}
