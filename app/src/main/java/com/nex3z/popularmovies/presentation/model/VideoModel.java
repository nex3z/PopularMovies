package com.nex3z.popularmovies.presentation.model;

public class VideoModel {

    public static final String YOUTUBE = "YouTube";

    private String mId;

    private String mIso;

    private String mKey;

    private String mName;

    private String mSite;

    private String mSize;

    private String mType;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getIso() {
        return mIso;
    }

    public void setIso(String iso) {
        this.mIso = iso;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        this.mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    @Override
    public String toString() {
        return "VideoModel: mId = " + mId + ", mIso = " + mIso + ", mKey = " + mKey + "mName = " + mName
                + ", mSite = " + mSite + ", mSize = " + mSize + ", mType = " + mType;

    }
}
