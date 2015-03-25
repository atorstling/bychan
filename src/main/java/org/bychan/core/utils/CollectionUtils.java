package org.bychan.core.utils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by alext on 2015-03-25.
 */
public class CollectionUtils {
    @NotNull
    public static <T> LinkedHashSet<T> getDuplicates(@NotNull final Iterable<T> iterable) {
        LinkedHashSet<T> duplicates = new LinkedHashSet<>();
        Set<T> dupeChecker = new LinkedHashSet<>();
        iterable.forEach((m) -> {
            if (!dupeChecker.add(m)) {
                duplicates.add(m);
            }
        });
        return duplicates;
    }
}
