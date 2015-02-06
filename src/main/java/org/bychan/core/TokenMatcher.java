package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2015-02-04
 * @param <M> match result type, if applicable.
 */
public interface TokenMatcher<M> {
    /**
     * Try to match agains the input string at given start location
     *
     * @return <code>null</code> if no match, otherwise a {@link org.bychan.core.TokenMatchResult} with the
     * index of the match end and any parsed object which you want to be able to pass to your parser callback.
     */
    @Nullable
    TokenMatchResult<M> tryMatch(@NotNull final String input, int searchStart);
}
