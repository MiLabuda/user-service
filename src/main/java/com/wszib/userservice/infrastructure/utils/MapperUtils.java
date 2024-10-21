package com.wszib.userservice.infrastructure.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    public static <T, R> R map(T t, Function<T, R> mapper) {
        return mapper.apply(t);
    }

    public static <T, R> List<R> mapList(List<T> list, Function<T, R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }
}
