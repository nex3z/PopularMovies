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
import com.nex3z.popularmovies.data.model.Video;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private static final String LOG_TAG = VideoAdapter.class.getSimpleName();

    private List<Video> mVideos;

    public VideoAdapter(List<Video> videos) {
        Log.v(LOG_TAG, "VideoAdapter(): videos size = " + videos.size());
        mVideos = videos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        Log.v(LOG_TAG, "onCreateViewHolder(): viewHolder = " + viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v(LOG_TAG, "onBindViewHolder(): position = " + position);
        Video video = mVideos.get(position);

        TextView textView = holder.videoTitleTextView;
        String title = video.getName();
        textView.setText(title);

        holder.videoIconImageView.setImageResource(R.drawable.placeholder_video);

    }

    @Override
    public int getItemCount() {
        Log.v(LOG_TAG, "getItemCount(): size = " + mVideos.size());
        return mVideos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.video_icon_image)
        ImageView videoIconImageView;
        @Bind(R.id.video_name_textview)
        TextView videoTitleTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.v(LOG_TAG, "ViewHolder(): Bind complete.");
        }
    }
}
