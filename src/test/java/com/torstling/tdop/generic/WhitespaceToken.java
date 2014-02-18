package com.torstling.tdop.generic;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class WhitespaceToken<N> implements Token<N> {
    private final WhitespaceTokenType<N> type;
    @NotNull
    private final LexingMatch match;

    public WhitespaceToken(WhitespaceTokenType<N> type, @NotNull final LexingMatch match) {
        this.type = type;
        this.match = match;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull N previous, @NotNull TokenParserCallback<N> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @NotNull
    @Override
    public N infixParse(@NotNull N previous, @NotNull TokenParserCallback<N> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @Override
    public int infixBindingPower() {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @NotNull
    @Override
    public TokenType<N> getType() {
        return type;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
