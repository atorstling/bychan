package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class DefinitionTokenType<N extends Node> implements TokenType<N> {
    @NotNull
    private final LeveledTokenDefinition<N> def;
    private TokenFinder<N> tokenFinder;

    public DefinitionTokenType(@NotNull final LeveledTokenDefinition<N> def, @NotNull final TokenFinder<N> tokenFinder) {
        this.def = def;
        this.tokenFinder = tokenFinder;
    }

    @Override
    public Token<N> toToken(@NotNull final LexingMatch match) {
        return new DefinitionToken<N>(this, match, def, tokenFinder);
    }

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
