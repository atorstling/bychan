package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InfixParser<N> {
    /**
     * Parse this token as an infix operator. A typical binary operator implementation uses the parser callback
     * to parse the RHS of the subExpression and returns a binary operator node linking the LHS and RHS together.
     *
     * @param previous the AST node resulting from parsing the subExpression to the previous of this token, up until this
     */
    @NotNull
    N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
