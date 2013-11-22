package com.torstling.tdop.fluid;

import com.torstling.tdop.core.AstNode;
import org.jetbrains.annotations.NotNull;

class LeveledTokenDefinition<N extends AstNode> {
    @NotNull
    private final TokenDefinition<N> definition;
    private final int levelCount;

    public LeveledTokenDefinition(@NotNull final TokenDefinition<N> definition, final int levelCount) {
        this.definition = definition;
        this.levelCount = levelCount;
    }

    public String getPattern() {
        return definition.getPattern();
    }

    public int getLevel() {
        return levelCount;
    }

    public String toString() {
        return levelCount + ":"  + definition;
    }

    public PrefixAstBuilder<N> getPrefixBuilder() {
        return definition.getPrefixBuilder();
    }

    @NotNull
    public TokenDefinition<N> getTokenDefinition() {
        return definition;
    }

    public InfixAstBuilder<N> getInfixBuilder() {
        return definition.getInfixBuilder();
    }
}
