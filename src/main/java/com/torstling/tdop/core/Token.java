package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A token in the lexing stream.
 *
 * @param <N>
 */
public interface Token<N, S> {
    /**
     * Parse this token as a prefix operator or standalone expression.
     * Feel free to continue the parsing using the provided parser callback,
     *
     * @return the resulting AST node.
     */
    @NotNull
    N prefixParse(@NotNull N previous, @NotNull TokenParserCallback<N, S> parser);

    /**
     * Parse this token as an infix operator. A typical binary operator implementation uses the parser callback
     * to parse the RHS of the expression and returns a binary operator node linking the LHS and RHS together.
     *
     * @param previous the AST node resulting from parsing the expression to the previous of this token, up until this
     */
    @NotNull
    N infixParse(@NotNull N previous, @NotNull TokenParserCallback<N, S> parser);

    /**
     * @return How strongly this token, when interpreted as an infix operator, binds to the previous argument.
     */
    int infixBindingPower();

    /**
     * Check the type of this token.
     */
    @NotNull
    TokenType<N, S> getType();

    /**
     * @return the lexing match which this token originated from
     */
    @NotNull
    LexingMatch getMatch();
}
