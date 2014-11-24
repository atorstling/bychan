package com.torstling.tdop.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A token signaling the end of the lexing stream
 *
 * @param <N>
 */
public class EndToken<N> implements Token<N> {


    private final LexingMatch lexingMatch;

    public EndToken(@NotNull final LexingMatch lexingMatch) {
        this.lexingMatch = lexingMatch;
    }

    @NotNull
    public N prefixParse(@Nullable N previous, @NotNull final TokenParserCallback<N> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse expression, end reached", new ParsingPosition(lexingMatch.getStartPosition(), lexingMatch.getText())));
    }

    @NotNull
    public N infixParse(@Nullable N previous, @NotNull TokenParserCallback<N> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse expression, end reached", new ParsingPosition(lexingMatch.getStartPosition(), lexingMatch.getText())));
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
        return "END";
    }
}
