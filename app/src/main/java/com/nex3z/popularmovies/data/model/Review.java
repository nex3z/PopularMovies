package com.nex3z.popularmovies.data.model;

import org.parceler.Parcel;
import org.parceler.Transient;


@Parcel
public class Review {

    @Transient
    private String id;

    @Transient
    private String author;

    @Transient
    private String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
