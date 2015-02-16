package org.bychan.core.utils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Created by alext on 2015-02-16.
 */
public class BoundedFifo<T> {

    private final int maxCapacity;
    private LinkedList<T> unbounded;

    public BoundedFifo(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.unbounded = new LinkedList<>();
    }

    public void put(@NotNull final T t) {
        unbounded.add(t);
        while (unbounded.size() > maxCapacity) {
            unbounded.remove();
        }
    }

    @NotNull
    public T getFromFront(final int i) {
        return unbounded.get(unbounded.size() - 1 - i);
    }
}
