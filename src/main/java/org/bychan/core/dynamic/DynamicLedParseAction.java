package org.bychan.core.dynamic;

import org.bychan.core.basic.Lexeme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link org.bychan.core.basic.LedParseAction}, but receives more runtime information
 * which is needed in the dynamic environment.
 *
 */
public interface DynamicLedParseAction<N> {
    N parse(@Nullable N previous, @NotNull UserParserCallback<N> parser, @NotNull Lexeme<N> lexeme);
}
