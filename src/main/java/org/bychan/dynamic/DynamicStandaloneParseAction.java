package org.bychan.dynamic;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A convenience class
 * @param <N>
 */
public interface DynamicStandaloneParseAction<N> {
    @NotNull
    N parse(@Nullable N previous, @NotNull LexingMatch match);
}
