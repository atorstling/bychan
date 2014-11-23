package com.torstling.tdop.fluid.json;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.fluid.StandaloneAstBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by alext on 2/26/14.
 */
public class NonTerminalProduction implements Production {
    @NotNull
    private final NonTerminal nonTerminal;

    public NonTerminalProduction(@NotNull NonTerminal nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    @NotNull
    @Override
    public String getName() {
        return nonTerminal.getName();
    }

    @NotNull
    @Override
    public String getLexerPattern() {
        return nonTerminal.getCombinedProductionsPattern();
    }

    @NotNull
    @Override
    public StandaloneAstBuilder<Object> getAstBuilder() {
        return new StandaloneAstBuilder<Object>() {
            @NotNull
            @Override
            public Object build(@Nullable Object previous, @NotNull LexingMatch match) {
                return nonTerminal.getProductionForMatch(match).getAstBuilder().build(previous, match);
            }
        };
    }
}
