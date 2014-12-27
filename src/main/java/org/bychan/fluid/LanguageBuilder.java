package org.bychan.fluid;

import org.jetbrains.annotations.NotNull;

public class LanguageBuilder<N> {
    @NotNull
    private final TokenDefinitions<N> tokens;
    @NotNull
    private String name;
    private int currentLevel;

    public LanguageBuilder() {
        this.tokens = new TokenDefinitions<>();
        this.name = "unnamed";
    }

    @NotNull
    public Language<N> completeLanguage() {
        return new Language<>(name, tokens);
    }

    @NotNull
    public TokenDefinitionBuilder<N> newToken() {
        return newTokenInternal();
    }

    @NotNull
    public LanguageBuilder<N> named(@NotNull final String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public LanguageBuilder<N> addToken(@NotNull TokenDefinition<N> tokenDefinition) {
        tokens.add(tokenDefinition);
        return this;
    }

    public LanguageBuilder<N> newLevel() {
        return this;
    }


    public TokenDefinitionBuilder<N> startToken() {
        return newTokenInternal();
    }

    private TokenDefinitionBuilder<N> newTokenInternal() {
        return new TokenDefinitionBuilder<>(this).leftBindingPower(currentLevel++);
    }

    public LanguageBuilder<N> newLowerPriorityLevel() {
        return this;
    }

    public LanguageBuilder<N> endLevel() {
        return this;
    }
}
