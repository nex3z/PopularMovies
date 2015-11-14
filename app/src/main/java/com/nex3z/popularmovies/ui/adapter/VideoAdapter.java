package com.nex3z.popularmovies.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.provider.MovieContract;

public class VideoAdapter extends CursorAdapter {

    private static final String LOG_TAG = VideoAdapter.class.getSimpleName();

    public VideoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v(LOG_TAG, "newView()");
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String videoPath = cursor.getString(cursor.getColumnIndex(MovieContract.VideoEntry.COLUMN_KEY));
        //String imageUrl = Utility.convertPosterImagePath(posterPath);


        String name = cursor.getString(cursor.getColumnIndex(MovieContract.VideoEntry.COLUMN_NAME));
        TextView textView = (TextView) view.findViewById(R.id.video_name_textview);
        textView.setText(name);

        Log.v(LOG_TAG, "bindView(): videoPath = " + videoPath + ", name = " + name);
    }
}
