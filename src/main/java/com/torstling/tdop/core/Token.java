package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token in the lexing stream.
 *
 * @param <N>
 */
public interface Token<N> {
    /**
     * Parse this token as a prefix operator or standalone subExpression.
     * Feel free to continue the parsing using the provided parser callback,
     *
     * @return the resulting AST node.
     */
    @NotNull
    N prefixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);

    /**
     * Parse this token as an infix operator. A typical binary operator implementation uses the parser callback
     * to parse the RHS of the subExpression and returns a binary operator node linking the LHS and RHS together.
     *
     * @param previous the AST node resulting from parsing the subExpression to the previous of this token, up until this
     */
    @NotNull
    N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser);

    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the previous argument.
     */
    int infixBindingPower();

    /**
     * Check the type of this token.
     */
    @NotNull
    TokenType<N> getType();

    /**
     * @return the lexing match which this token originated from
     */
    @NotNull
    LexingMatch getMatch();
}
