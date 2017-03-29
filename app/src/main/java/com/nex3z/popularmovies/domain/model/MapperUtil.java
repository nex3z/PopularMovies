package com.nex3z.popularmovies.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    public static <From, To> List<To> transform(List<From> fromList,
                                                Transformer<From, To> transformer) {
        List<To> toList = null;

        if (fromList != null) {
            toList = new ArrayList<To>(fromList.size());
            for (From from : fromList) {
                toList.add(transformer.transform(from));
            }
        }

        return toList;
    }

    public interface Transformer<From, To> {
        To transform(From from);
    }

}
