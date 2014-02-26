package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.StandaloneAstBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by alext on 2/26/14.
 */
public class NonTerminalProduction implements Production {
    @NotNull
    private final NonTerminal nonTerminal;

    public NonTerminalProduction(NonTerminal nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    @NotNull
    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public String getLexerPattern() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public StandaloneAstBuilder<Object> getAstBuilder() {
        throw new UnsupportedOperationException();
    }
}
