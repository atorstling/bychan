package com.torstling.tdop.fluid.json;

import com.torstling.tdop.fluid.StandaloneAstBuilder;

public class Production {
    private final String lexerPattern;
    private final StandaloneAstBuilder<Object> astBuilder;

    public Production(String lexerPattern, StandaloneAstBuilder<Object> astBuilder) {
        this.lexerPattern = lexerPattern;
        this.astBuilder = astBuilder;
    }

    public String getLexerPattern() {
        return lexerPattern;
    }

    public StandaloneAstBuilder<Object> getAstBuilder() {
        return astBuilder;
    }
}
