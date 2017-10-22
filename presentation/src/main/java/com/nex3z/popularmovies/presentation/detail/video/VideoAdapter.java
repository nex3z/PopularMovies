package com.nex3z.popularmovies.presentation.detail.video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.video.VideoModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<VideoModel> mVideos;
    private OnVideoClickListener mListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(itemView);
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

    public void setVideos(List<VideoModel> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public void setOnVideoClickListener(OnVideoClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_video_title) TextView mTvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vg_item_video_container)
        void onItemClick() {
            if (mListener != null) {
                mListener.onVideoClick(getAdapterPosition());
            }
        }
    }

    public interface OnVideoClickListener {
        void onVideoClick(int position);
    }

}