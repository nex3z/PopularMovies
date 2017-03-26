package com.nex3z.popularmovies.data.entity.video;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoRespEntity {

    @SerializedName("id") private long mId;
    @SerializedName("results") private List<VideoEntity> mResults;

    @Override
    public String toString() {
        return "VideoRespEntity{" +
                "mId=" + mId +
                ", mResults=" + mResults +
                '}';
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<VideoEntity> getResults() {
        return mResults;
    }

    public void setResults(List<VideoEntity> results) {
        mResults = results;
    }
}
