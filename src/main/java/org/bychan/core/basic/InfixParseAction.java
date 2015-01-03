package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An infix parse action. a.k.a LED (Left Denotation) in the original paper
 *
 */
public interface InfixParseAction<N> {
    /**
     * Parse this lexeme as an infix operator. A typical binary operator implementation uses the parser callback
     * to parse the RHS of the expression and returns a binary operator node linking the LHS and RHS together.
     */
    @NotNull
    N parse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
