package com.nex3z.popularmovies.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Transient;

@Parcel
public class Video {

    @Transient
    private String id;

    @SerializedName("iso_639_1") @Transient
    private String iso;

    @Transient
    private String key;

    @Transient
    private String name;

    @Transient
    private String site;

    @Transient
    private String size;

    @Transient
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
