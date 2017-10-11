package com.nex3z.popularmovies.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {
    private MapperUtil() {}

    public static <From, To> List<To> transform(List<From> fromList, Mapper<From, To> mapper) {
        List<To> toList = new ArrayList<>();
        for (From from : fromList) {
            toList.add(mapper.transform(from));
        }
        return toList;
    }

    public interface Mapper<From, To> {
        To transform(From from);
    }

}
