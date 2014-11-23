package com.torstling.tdop.fluid.json.bnf;

import com.torstling.tdop.fluid.StandaloneAstBuilder;
import org.jetbrains.annotations.NotNull;

public class TerminalProduction implements Production {
    private final String lexerPattern;
    private final StandaloneAstBuilder<Object> astBuilder;

    public TerminalProduction(String lexerPattern, StandaloneAstBuilder<Object> astBuilder) {
        this.lexerPattern = lexerPattern;
        this.astBuilder = astBuilder;
    }

    @NotNull
    @Override
    public String getName() {
        return lexerPattern;
    }

    @NotNull
    public String getLexerPattern() {
        return lexerPattern;
    }

    @NotNull
    public StandaloneAstBuilder<Object> getAstBuilder() {
        return astBuilder;
    }
}
