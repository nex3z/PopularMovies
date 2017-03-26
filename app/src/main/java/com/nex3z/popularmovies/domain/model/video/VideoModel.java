package com.nex3z.popularmovies.domain.model.video;

public class VideoModel {

    private String mId;
    private String mIso6391;
    private String mIso31661;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mType;

    @Override
    public String toString() {
        return "VideoModel{" +
                "mId='" + mId + '\'' +
                ", mIso6391='" + mIso6391 + '\'' +
                ", mIso31661='" + mIso31661 + '\'' +
                ", mKey='" + mKey + '\'' +
                ", mName='" + mName + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mSize=" + mSize +
                ", mType='" + mType + '\'' +
                '}';
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIso6391() {
        return mIso6391;
    }

    public void setIso6391(String iso6391) {
        mIso6391 = iso6391;
    }

    public String getIso31661() {
        return mIso31661;
    }

    public void setIso31661(String iso31661) {
        mIso31661 = iso31661;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
