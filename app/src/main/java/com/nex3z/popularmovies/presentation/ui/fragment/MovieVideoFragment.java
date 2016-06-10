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
import com.nex3z.popularmovies.data.entity.mapper.VideoEntityDataMapper;
import com.nex3z.popularmovies.data.executor.JobExecutor;
import com.nex3z.popularmovies.data.repository.VideoDataRepository;
import com.nex3z.popularmovies.data.repository.datasource.video.VideoDataStoreFactory;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoList;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.VideoRepository;
import com.nex3z.popularmovies.presentation.UIThread;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;
import com.nex3z.popularmovies.presentation.presenter.MovieVideoPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieVideoView;
import com.nex3z.popularmovies.presentation.ui.adapter.VideoAdapter;
import com.nex3z.popularmovies.presentation.ui.misc.DividerItemDecoration;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieVideoFragment extends Fragment implements MovieVideoView {
    private static final String LOG_TAG = MovieVideoFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.rv_video_list) RecyclerView mVideoRecyclerView;
    @BindView(R.id.pb_load_video) ProgressBar mProgressBar;

    private MovieModel mMovie;
    private VideoAdapter mAdapter;
    private MovieVideoPresenter mPresenter;

    public MovieVideoFragment() {}

    public static MovieVideoFragment newInstance(Parcelable parcelable) {
        MovieVideoFragment fragment = new MovieVideoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_movie_video, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadVideos();
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
    public void renderVideos(Collection<VideoModel> videoModelCollection) {
        if (videoModelCollection != null) {
            mAdapter.setVideoCollection(videoModelCollection);
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
        VideoRepository videoRepo = new VideoDataRepository(
                new VideoDataStoreFactory(), new VideoEntityDataMapper());
        UseCase getVideoList = new GetVideoList(videoRepo, new JobExecutor(), new UIThread());
        mPresenter = new MovieVideoPresenter(mMovie, getVideoList, new VideoModelDataMapper());
        mPresenter.setView(this);

        setupRecyclerView();
    }

    private void loadVideos() {
        mPresenter.initialize();
    }

    private void setupRecyclerView() {
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
