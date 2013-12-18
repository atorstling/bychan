package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class GenericTokenType<N extends AstNode> implements TokenType<N> {
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public GenericTokenType(@NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull final LexingMatch match) {
        return new GenericToken<>(this, match, def, tokenFinder);
    }

    @NotNull
    @Override
    public String getPattern() {
        return def.getPattern();
    }

    @Override
    public boolean shouldSkip() {
        return def.getTokenDefinition().shouldFilterOutBeforeParsing();
    }

    public String toString() {
        return def.getTokenTypeName();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def.getTokenDefinition();
    }
}
