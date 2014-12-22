package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LanguageBuilder<N> {
    @NotNull
    private final List<TokenDefinitions<N>> levels;
    @NotNull
    private String name;

    public LanguageBuilder() {
        this.levels = new ArrayList<>();
        this.name = "unnamed";
    }

    @NotNull
    public LevelLanguageBuilder<N> newLowerPriorityLevel() {
        return new LevelLanguageBuilder<>(this);
    }

    void addLevel(List<TokenDefinition<N>> tokens) {
        this.levels.add(new TokenDefinitions<>(tokens));
    }

    @NotNull
    public Language<N> completeLanguage() {
        return new Language<>(name, levels);
    }

    @NotNull
    public TokenDefinitionBuilder<N> newToken() {
        return new TokenDefinitionBuilder<>();
    }

    @NotNull
    public LanguageBuilder<N> named(@NotNull final String name) {
        this.name = name;
        return this;
    }
}
