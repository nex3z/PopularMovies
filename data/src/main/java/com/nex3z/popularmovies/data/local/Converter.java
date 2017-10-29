package com.nex3z.popularmovies.data.local;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public static List<Integer> stringToIntegerList(String str) {
        String[] items = str.split("\\s*,\\s*");
        List<Integer> list = new ArrayList<>(items.length);
        for (String item : items) {
            list.add(Integer.parseInt(item));
        }
        return list;
    }

    @TypeConverter
    public static String integerListToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int item : list) {
            sb.append(item);
            sb.append(",");
        }
        return sb.toString();
    }

}
