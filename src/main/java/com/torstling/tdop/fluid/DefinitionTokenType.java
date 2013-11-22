package com.torstling.tdop.fluid;

import com.torstling.tdop.*;
import org.jetbrains.annotations.NotNull;

public class DefinitionTokenType<N extends Node> implements TokenType<N> {
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private final TokenFinder<N> tokenFinder;

    public DefinitionTokenType(@NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull final LexingMatch match) {
        return new DefinitionToken<>(this, match, def, tokenFinder);
    }

    @NotNull
    @Override
    public String getPattern() {
        return def.getPattern();
    }

    public String toString() {
        return "type of '" + def.getPattern() + "'" + System.identityHashCode(this);
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return def.getTokenDefinition();
    }
}
