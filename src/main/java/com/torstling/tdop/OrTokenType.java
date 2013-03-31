package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class OrTokenType implements TokenType<BooleanExpressionNode> {
    public static final OrTokenType INSTANCE = new OrTokenType();

    public Token<BooleanExpressionNode> toToken(LexingMatch match) {
        return new OrToken(match);
    }

    public String getPattern() {
        return "\\+";
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
