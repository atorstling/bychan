package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An infix parse action. a.k.a LED (Left Denotation) in the original paper
 * @param <N>
 */
public interface InfixParseAction<N> {
    /**
     * Parse this token as an infix operator. A typical binary operator implementation uses the parser callback
     * to parse the RHS of the expression and returns a binary operator node linking the LHS and RHS together.
     *
     * @param previous the AST node resulting from parsing the expression to the previous of this token, up until this
     */
    @NotNull
    N parse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
