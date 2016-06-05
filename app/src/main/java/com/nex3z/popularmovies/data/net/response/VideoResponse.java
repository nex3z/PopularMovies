package com.nex3z.popularmovies.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.entity.VideoEntity;

import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    private long movieId;

    @SerializedName("results")
    private List<VideoEntity> videos;

    public List<VideoEntity> getVideos() {
        return videos;
    }
}
