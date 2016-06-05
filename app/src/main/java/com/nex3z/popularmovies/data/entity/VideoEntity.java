package com.nex3z.popularmovies.data.entity;

import com.google.gson.annotations.SerializedName;

public class VideoEntity {

    private String id;

    @SerializedName("iso_639_1")
    private String iso;

    private String key;

    private String name;

    private String site;

    private String size;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final String YOUTUBE = "YouTube";

    @Override
    public String toString() {
        return "Video: id = " + id + ", iso = " + iso + ", key = " + key + "name = " + name
                + ", site = " + site + ", size = " + size + ", type = " + type;

    }
}
