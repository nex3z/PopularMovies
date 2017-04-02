package com.nex3z.popularmovies.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.video.VideoModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private static final String LOG_TAG = VideoAdapter.class.getSimpleName();

    private static OnItemClickListener mListener;
    private List<VideoModel> mVideos;

    public interface OnItemClickListener {
        void onItemClick(int position, VideoAdapter.ViewHolder vh);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.video_item, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoModel video = mVideos.get(position);
        holder.mTvTitle.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return mVideos == null ? 0 : mVideos.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setVideos(List<VideoModel> videos) {
        validateVideos(videos);
        mVideos = videos;
        notifyDataSetChanged();
    }

    private void validateVideos(List<VideoModel> videos) {
        if (videos == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = ViewHolder.class.getSimpleName();
        @BindView(R.id.tv_video_title) TextView mTvTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(LOG_TAG, "onClick(): mListener = " + mListener);
                    if (mListener != null)
                        mListener.onItemClick(getLayoutPosition(), ViewHolder.this);
                }
            });
        }
    }
}
