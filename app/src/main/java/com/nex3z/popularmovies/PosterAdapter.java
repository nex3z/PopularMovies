package com.nex3z.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.nex3z.popularmovies.data.MovieContract;
import com.nex3z.popularmovies.util.Utility;
import com.squareup.picasso.Picasso;


public class PosterAdapter extends CursorAdapter {

    private static final String LOG_TAG = PosterAdapter.class.getSimpleName();

    public PosterAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v(LOG_TAG, "newView()");
        View view = LayoutInflater.from(context).inflate(R.layout.poster_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        String imageUrl = Utility.convertPosterImagePath(posterPath);
        Log.v(LOG_TAG, "bindView(): imageUrl = " + imageUrl);

        ImageView iconView = (ImageView) view.findViewById(R.id.poster_image);
        // Picasso.with(context).setIndicatorsEnabled(true);
        Picasso.with(context).load(imageUrl).into(iconView);
    }
}
