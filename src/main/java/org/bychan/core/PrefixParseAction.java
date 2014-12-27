package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A prefix parse action. A.k.a NUD (Null Denotion) in the original paper.
 * @param <N>
 */
public interface PrefixParseAction<N> {
    /**
     * Parse this token as a prefix operator or standalone expression.
     * Feel free to continue the parsing using the provided parser callback.
     *
     * @return the resulting AST node.
     */
    @NotNull
    N parse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
