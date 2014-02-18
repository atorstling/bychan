package com.torstling.tdop.fluid;

import java.util.ArrayList;
import java.util.List;

public class LevelLanguageBuilder<N> {
    private final LanguageBuilder<N> languageBuilder;
    private final List<TokenDefinition<N>> tokens;

    public LevelLanguageBuilder(LanguageBuilder<N> languageBuilder) {
        this.languageBuilder = languageBuilder;
        this.tokens = new ArrayList<>();
    }

    public LevelLanguageBuilder<N> addToken(TokenDefinition<N> token) {
        this.tokens.add(token);
        return this;
    }

    public LanguageBuilder<N> endLevel() {
        languageBuilder.addLevel(tokens);
        return languageBuilder;
    }
}
