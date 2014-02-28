package com.torstling.tdop.fluid;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Created by alext on 2/28/14.
 */
public class StatementDefinitionBuilder<N> {
    @NotNull
    private String name;
    @NotNull
    private PrefixAstBuilder<N> prefixAstBuilder;

    public StatementDefinitionBuilder<N> named(@NotNull final String statementName) {
        name = statementName;
        return this;
    }

    public StatementDefinitionBuilder<N> as(@NotNull final PrefixAstBuilder<N> prefixAstBuilder) {
        this.prefixAstBuilder = prefixAstBuilder;
        return this;
    }

    @NotNull
    public TokenDefinition<N> build() {
        return new TokenDefinition<N>(Pattern.compile(""), prefixAstBuilder, null, name, true);
    }
}
