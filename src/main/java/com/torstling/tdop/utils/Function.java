package com.torstling.tdop.utils;

import org.jetbrains.annotations.NotNull;

public interface Function<F, T> {
    @NotNull
    public T apply(@NotNull final F f);
}