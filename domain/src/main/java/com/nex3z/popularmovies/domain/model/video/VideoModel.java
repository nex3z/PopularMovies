package com.nex3z.popularmovies.domain.model.video;

public class VideoModel {
    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private final String id;

    private String iso6391;

    private String iso31661;

    private String key;

    private String name;

    private String site;

    private int size;

    private String type;

    public String getVideoUrl() {
        return YOUTUBE_BASE_URL + key;
    }

    public VideoModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "id='" + id + '\'' +
                ", iso6391='" + iso6391 + '\'' +
                ", iso31661='" + iso31661 + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }
}
