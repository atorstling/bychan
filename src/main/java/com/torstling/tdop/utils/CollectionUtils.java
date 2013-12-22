package com.torstling.tdop.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {
    @NotNull
    public static <S, T> Collection<T> transform(@NotNull final Collection<? extends S> ss, @NotNull final Function<S, T> f) {
        ArrayList<T> ts = new ArrayList<>(ss.size());
        for (S s : ss) {
            ts.add(f.apply(s));
        }
        return ts;
    }

    @NotNull
    public static <S> Function<S, String> toStringFunction() {
        return new Function<S, String>() {
            @NotNull
            @Override
            public String apply(@NotNull S s) {
                return s.toString();
            }
        };
    }
}
