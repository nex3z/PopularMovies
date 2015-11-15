package com.nex3z.popularmovies.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Video;
import com.nex3z.popularmovies.data.provider.MovieContract;
import com.nex3z.popularmovies.service.MovieService;
import com.nex3z.popularmovies.ui.adapter.VideoAdapter;


public class VideoListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = VideoListFragment.class.getSimpleName();

    public static String DETAIL_URI = "DETAIL_URI";

    private ListView mListView;
    private VideoAdapter mVideoAdapter;
    private String mMovieId;

    private static final String[] VIDEO_COLUMNS = {
            MovieContract.VideoEntry._ID,
            MovieContract.VideoEntry.COLUMN_KEY,
            MovieContract.VideoEntry.COLUMN_NAME,
            MovieContract.VideoEntry.COLUMN_SITE
    };

    static final int COL_VIDEO_ID = 0;
    static final int COL_VIDEO_KEY = 1;
    static final int COL_VIDEO_NAME = 2;
    static final int COL_VIDEO_SITE = 3;

    private static final int VIDEO_LOADER = 0;

    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            Uri uri = arguments.getParcelable(VideoListFragment.DETAIL_URI);
            Log.v(LOG_TAG, "onCreate(): Uri = " + uri);
            mMovieId = uri.getLastPathSegment();
            Log.v(LOG_TAG, "movie id = " + mMovieId);
            updateVideos(Long.parseLong(mMovieId));
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mVideoAdapter = new VideoAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_video_list, container, false);

        mListView = (ListView) rootView.findViewById(R.id.video_list);
        mListView.setAdapter(mVideoAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                Log.v(LOG_TAG, "onItemClick(): position = " + String.valueOf(position) + ", cursor = " + cursor);
                if (cursor != null) {
                    playVideo(cursor.getString(COL_VIDEO_SITE), cursor.getString(COL_VIDEO_KEY));
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(VIDEO_LOADER, null, this);
        Log.v(LOG_TAG, "onActivityCreated(): Loader initiated.");

        super.onActivityCreated(savedInstanceState);
    }

    void updateVideos(long movieId) {
        Log.v(LOG_TAG, "updateVideos(): movieId = " + movieId);
        MovieService.startActionFetchVideo(getActivity(), movieId);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieContract.VideoEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(mMovieId)).build();
        Log.v(LOG_TAG, "onCreateLoader(): uri = " + uri);

        return new CursorLoader(getActivity(),
                uri,
                VIDEO_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "onLoadFinished(): data = " + data);
        mVideoAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(LOG_TAG, "onLoaderReset()");
        mVideoAdapter.swapCursor(null);
    }

    private void playVideo(String site, String key) {
        final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
        Log.v(LOG_TAG, "playVideo(): site = " + site + ", key = " + key);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = null;
        switch (site) {
            case Video.YOUTUBE: {
                uri = Uri.parse(YOUTUBE_BASE_URL + key);
                break;
            }
            default: {
                uri = Uri.parse(YOUTUBE_BASE_URL + key);
            }
        }

        intent.setData(uri);
        startActivity(intent);
    }
}
