package org.bychan.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrefixParser<N> {
    /**
     * Parse this token as a prefix operator or standalone subExpression.
     * Feel free to continue the parsing using the provided parser callback,
     *
     * @return the resulting AST node.
     */
    @NotNull
    N prefixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);
}
