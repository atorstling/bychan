package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * An interface to the parser, which the nodes can use to
 * continue the parsing
 */
public interface TokenParserCallback<N extends AstNode> {
    /**
     * Parse upcoming tokens from the stream into an expression, and keep going
     * until token binding powers drop down to or below the supplied floor. If this
     * feels backwards, remember that weak operands end up higher in the parse tree, consider for instance
     * <code>1*2 + 3 </code> which becomes
     * <pre>
     *       +
     *      / \
     *    1*2  3
     * </pre>.
     * To parse this expression, we start by swallowing "1*2", stopping by "+". This is achieved by calling
     * this method with the lower binding power of "+" as an argument.
     */
    @NotNull
    N expression(int powerFloor);

    /**
     * Swallow a token of the specified type.
     */
    @NotNull
    Token<N> swallow(@NotNull TokenType<N> type);

    @NotNull
    Token<N> peek();
}
