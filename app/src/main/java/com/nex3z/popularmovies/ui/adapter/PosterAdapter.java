package com.nex3z.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.util.ImageUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder>{

    private static final String LOG_TAG = PosterAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movie> mMovies;
    private LayoutInflater mLayoutInflater = null;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public PosterAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.poster_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        TextView textView = holder.titleTextView;
        String title = movie.getTitle();
        textView.setText(title);

        String url = ImageUtility.getImageUrl(movie.getPosterPath());
        Log.v(LOG_TAG, "getView(): title = " + title + ", url = " + url);
        // Picasso.with(mContext).setIndicatorsEnabled(true);
        Picasso.with(mContext)
                .load(url)
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String LOG_TAG = ViewHolder.class.getSimpleName();

        @Bind(R.id.poster_image) ImageView posterImageView;
        @Bind(R.id.poster_title) TextView titleTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.v(LOG_TAG, "ViewHolder(): Bind complete.");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(LOG_TAG, "onClick(): mListener = " + mListener);
                    if (mListener != null)
                        mListener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

}
