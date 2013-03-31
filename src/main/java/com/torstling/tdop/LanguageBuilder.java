package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageBuilder<N extends Node> {
    @NotNull
    private final List<TokenDefinitions<N>> levels;
    @NotNull
    private final List<TokenDefinition<N>> currentTokens;

    public LanguageBuilder() {
        this.levels = new ArrayList<>();
        this.currentTokens = new ArrayList<>();
    }

    @NotNull
    public LanguageBuilder<N> addToken(@NotNull final TokenDefinition<N> token) {
        currentTokens.add(token);
        return this;
    }

    @NotNull
    public LanguageBuilder<N> newLevel() {
        this.levels.add(new TokenDefinitions<>(currentTokens));
        currentTokens.clear();
        return this;
    }

    @NotNull
    public Language<N> build() {
        flushRemainingTokens();
        return new Language<N>(levels);
    }

    private void flushRemainingTokens() {
        if (!currentTokens.isEmpty()) {
            newLevel();
        }
    }
}
