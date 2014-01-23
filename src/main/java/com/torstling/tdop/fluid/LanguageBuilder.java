package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LanguageBuilder<N extends AstNode,S> {
    @NotNull
    private final List<TokenDefinitions<N, S>> levels;
    @NotNull
    private final List<TokenDefinition<N, S>> currentTokens;

    public LanguageBuilder() {
        this.levels = new ArrayList<>();
        this.currentTokens = new ArrayList<>();
    }

    @NotNull
    public LanguageBuilder<N,S> addToken(@NotNull final TokenDefinition<N, S> token) {
        currentTokens.add(token);
        return this;
    }

    @NotNull
    public LanguageBuilder<N,S> newLowerPriorityLevel() {
        this.levels.add(new TokenDefinitions<N, S>(currentTokens));
        currentTokens.clear();
        return this;
    }

    @NotNull
    public Language<N,S> completeLanguage() {
        flushRemainingTokens();
        return new Language<>(levels);
    }

    private void flushRemainingTokens() {
        if (!currentTokens.isEmpty()) {
            newLowerPriorityLevel();
        }
    }

    @NotNull
    public TokenDefinitionBuilder<N, S> newToken() {
        return new TokenDefinitionBuilder<N, S>();
    }
}
