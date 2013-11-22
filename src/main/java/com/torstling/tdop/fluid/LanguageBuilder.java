package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LanguageBuilder<N extends AstNode> {
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
        return new Language<>(levels);
    }

    private void flushRemainingTokens() {
        if (!currentTokens.isEmpty()) {
            newLevel();
        }
    }
}