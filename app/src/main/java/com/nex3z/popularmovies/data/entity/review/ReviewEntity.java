package com.nex3z.popularmovies.data.entity.review;

import com.google.gson.annotations.SerializedName;

public class ReviewEntity {

    @SerializedName("id") private String mId;
    @SerializedName("author") private String mAuthor;
    @SerializedName("content") private String mContent;
    @SerializedName("url") private String mUrl;

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "mId='" + mId + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
