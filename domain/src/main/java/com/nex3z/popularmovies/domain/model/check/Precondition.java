package com.nex3z.popularmovies.domain.model.check;

public class Precondition {

    private Precondition() {}

    public static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
        return ref;
    }

    public static <T> T checkTransformValueNotNull(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot transform null value");
        }
        return value;
    }

}
