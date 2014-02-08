package com.torstling.tdop.generic;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class WhitespaceToken<N, S> implements Token<N, S> {
    private final WhitespaceTokenType<N, S> type;
    @NotNull
    private final LexingMatch match;

    public WhitespaceToken(WhitespaceTokenType<N, S> type, @NotNull final LexingMatch match) {
        this.type = type;
        this.match = match;
    }

    @NotNull
    @Override
    public N prefixParse(@NotNull S parent, @NotNull TokenParserCallback<N, S> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @NotNull
    @Override
    public N infixParse(S parent, @NotNull N left, @NotNull TokenParserCallback<N, S> parser) {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @Override
    public int infixBindingPower() {
        throw new ParsingFailedException(new ParsingFailedInformation("Cannot parse whitespace", match));
    }

    @NotNull
    @Override
    public TokenType<N, S> getType() {
        return type;
    }

    @NotNull
    @Override
    public LexingMatch getMatch() {
        return match;
    }
}
