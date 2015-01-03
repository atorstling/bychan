package org.bychan.core.dynamic;

import org.bychan.core.basic.LexingMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.LedParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 *
 */
public interface DynamicInfixParseAction<N> {
    N parse(@Nullable N previous, @NotNull LexingMatch match, @NotNull UserParserCallback<N> parser, int currentBindingPower);
}
