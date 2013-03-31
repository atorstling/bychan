package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class OrTokenType implements TokenType<BooleanExpressionNode> {
    private static final OrTokenType INSTANCE = new OrTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new OrToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\+";
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
