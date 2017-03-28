package com.nex3z.popularmovies.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MapperUtil {

    public static <From, To> List<To> transform(List<From> fromList,
                                                Function<From, To> transformer) {
        List<To> toList = null;

        if (fromList != null) {
            toList = new ArrayList<To>(fromList.size());
            for (From from : fromList) {
                toList.add(transformer.apply(from));
            }
        }

        return toList;
    }

}
