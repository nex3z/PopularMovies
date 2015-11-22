package com.nex3z.popularmovies.data.rest.model;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.model.Video;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class VideoResponse {
    @SerializedName("id")
    long movieId;

    @SerializedName("results")
    List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

}
