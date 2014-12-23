package org.bychan.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class Lists {

    private Lists() {}

    @NotNull
    public static <S, T> Collection<T> transform(@NotNull final Collection<? extends S> ss, @NotNull final Function<S, T> f) {
        return ss.stream().map(f::apply).collect(Collectors.toList());
    }

}
