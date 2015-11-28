package com.nex3z.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Video;
import com.nex3z.popularmovies.data.rest.model.VideoResponse;
import com.nex3z.popularmovies.data.rest.service.VideoService;
import com.nex3z.popularmovies.ui.adapter.VideoAdapter;
import com.nex3z.popularmovies.ui.widget.DividerItemDecoration;
import com.nex3z.popularmovies.util.VideoUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class VideoFragment extends Fragment {

    private static final String LOG_TAG = VideoFragment.class.getSimpleName();

    public static final String ARG_MOVIE_ID = "MOVIE_ID";

    //@Bind(R.id.video_container) NestedScrollView mScrollView;
    @Bind(R.id.video_list) RecyclerView mVideoList;

    private long mMovieId = -1;
    private VideoAdapter mVideoAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Video> mVideos = new ArrayList<Video>();

    public VideoFragment() { }

    public static VideoFragment newInstance(long movieId) {
        VideoFragment fragment = new VideoFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(VideoFragment.ARG_MOVIE_ID, movieId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_MOVIE_ID)) {
            mMovieId = getArguments().getLong(ARG_MOVIE_ID);
            Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovieId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);

        setupRecyclerView(mVideoList);
        mVideoAdapter = new VideoAdapter(mVideos);

        mVideoAdapter.setOnItemClickListener((view, position) -> {
            Log.v(LOG_TAG, "onItemClick(): position = " + position);
            Video video = mVideos.get(position);
            if (video != null) {
                Log.v(LOG_TAG, "onItemClick(): video = " + video);
                VideoUtility.playVideo(getContext(), video.getSite(), video.getKey());
            }
        });
        mVideoList.setAdapter(mVideoAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fetchVideos(mMovieId);
    }

    public void fetchVideos(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        VideoService service = App.getRestClient().getVideoService();
        service.getVideos(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processVideoResponse(response),
                        throwable -> Snackbar.make(
                                mVideoList,
                                throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG
                        ).show()
                );
    }

    private void processVideoResponse(VideoResponse response) {
        List<Video> videos = response.getVideos();
        Log.v(LOG_TAG, "processVideoResponse(): videos size = " + videos.size());
        mVideos.addAll(videos);
        Log.v(LOG_TAG, "processVideoResponse(): mVideos size = " + mVideos.size());
        for(Video video : mVideos) {
            Log.v(LOG_TAG, "processVideoResponse(): video key = " + video.getKey()
                    + ", name = " + video.getName());
        }
        mVideoAdapter.notifyDataSetChanged();
        Log.v(LOG_TAG, "processVideoResponse(): size = " + response.getVideos().size());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
