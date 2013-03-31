package com.torstling.tdop;

import com.google.common.base.Function;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Utils2 {
    @NotNull
    public static <S,T> Collection<T> transform(@NotNull final Collection<? extends S> ss, @NotNull final Function<S, T> f) {
        ArrayList<T> ts = new ArrayList<>(ss.size());
        for (S s : ss) {
            ts.add(f.apply(s));
        }
        return ts;
    }
}
