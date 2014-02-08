package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;

/**
 * A token signaling the end of the lexing stream
 *
 * @param <N>
 */
public class EndToken<N, S> implements Token<N, S> {


    private final LexingMatch lexingMatch;

    public EndToken(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }

    @NotNull
    public N prefixParse(@NotNull S parent, @NotNull final TokenParserCallback<N, S> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse expression, end reached", lexingMatch));
    }

    @NotNull
    public N infixParse(S parent, @NotNull N left, @NotNull TokenParserCallback<N, S> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse expression, end reached", lexingMatch));
    }

    public int infixBindingPower() {
        return 0;
    }


    @Override
    @NotNull
    public TokenType<N, S> getType() {
        return EndTokenType.get();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public LexingMatch getMatch() {
        return lexingMatch;
    }

    public String toString() {
        return "END";
    }
}
