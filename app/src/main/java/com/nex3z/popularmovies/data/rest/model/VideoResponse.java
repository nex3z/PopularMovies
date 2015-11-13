package com.nex3z.popularmovies.data.rest.model;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.model.Video;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class VideoResponse {
    @SerializedName("id")
    public long movieId;

    @SerializedName("results")
    public List<Video> videos;
}
