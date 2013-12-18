package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A token signaling the end of the lexing stream
 *
 * @param <N>
 */
public class EndToken<N extends AstNode> implements Token<N> {


    private final LexingMatch lexingMatch;

    public EndToken(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }

    @NotNull
    public N prefixParse(@NotNull final TokenParserCallback parser) {
        throw new IllegalStateException("Cannot parse end as expression");
    }

    @NotNull
    public N infixParse(@NotNull N left, @NotNull TokenParserCallback<N> parser) {
        throw new UnsupportedOperationException();
    }

    public int infixBindingPower() {
        return 0;
    }


    @Override
    @NotNull
    public TokenType<N> getType() {
        return EndTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return lexingMatch;
    }

    public String toString() {
        return ".";
    }
}
