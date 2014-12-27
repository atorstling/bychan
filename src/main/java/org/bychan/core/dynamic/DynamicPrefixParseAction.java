package org.bychan.core.dynamic;

import org.bychan.core.basic.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.PrefixParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 * @param <N>
 */
public interface DynamicPrefixParseAction<N> {
    @NotNull
    N parse(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser);
}
