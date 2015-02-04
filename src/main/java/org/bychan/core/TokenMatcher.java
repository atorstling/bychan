package org.bychan.core;

import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2015-02-04.
 */
public interface TokenMatcher {
    /**
     * Try to match agains the input string at given start location
     *
     * @return <code>-1</code> if no match, otherwise the index of the match end.
     */
    int tryMatch(@NotNull final String input, int searchStart);
}
