package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LevelLanguageBuilder<N extends AstNode, S> {
    private final LanguageBuilder<N, S> languageBuilder;
    private final List<TokenDefinition<N, S>> tokens;

    public LevelLanguageBuilder(LanguageBuilder<N, S> languageBuilder) {
        this.languageBuilder = languageBuilder;
        this.tokens = new ArrayList<>();
    }

    public LevelLanguageBuilder<N, S> addToken(TokenDefinition<N, S> token) {
        this.tokens.add(token);
        return this;
    }

    public LanguageBuilder<N, S> endLevel() {
        languageBuilder.addLevel(tokens);
        return languageBuilder;
    }
}
