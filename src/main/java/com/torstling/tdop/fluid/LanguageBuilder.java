package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LanguageBuilder<N, S> {
    @NotNull
    private final List<TokenDefinitions<N, S>> levels;

    public LanguageBuilder() {
        this.levels = new ArrayList<>();
    }

    @NotNull
    public LevelLanguageBuilder<N, S> newLowerPriorityLevel() {
        return new LevelLanguageBuilder<>(this);
    }

    void addLevel(List<TokenDefinition<N, S>> tokens) {
        this.levels.add(new TokenDefinitions<>(tokens));
    }

    @NotNull
    public Language<N, S> completeLanguage() {
        return new Language<>(levels);
    }

    @NotNull
    public TokenDefinitionBuilder<N, S> newToken() {
        return new TokenDefinitionBuilder<>();
    }
}
