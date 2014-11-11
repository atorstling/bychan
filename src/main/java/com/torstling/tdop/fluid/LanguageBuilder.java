package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LanguageBuilder<N> {
    @NotNull
    private final List<TokenDefinitions<N>> levels;

    public LanguageBuilder() {
        this.levels = new ArrayList<>();
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
        return new Language<>(levels);
    }

    @NotNull
    public TokenDefinitionBuilder<N> newToken() {
        return new TokenDefinitionBuilder<>();
    }
}
