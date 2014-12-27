package org.bychan.dynamic;

import org.bychan.core.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.PrefixParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 * @param <N>
 */
public interface DynamicPrefixParseAction<N> {
    @NotNull
    N parse(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser);
}
