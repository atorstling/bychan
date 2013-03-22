package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class OrTokenType implements TokenType<BooleanExpressionNode> {
    public static final OrTokenType INSTANCE = new OrTokenType();

    public Token<BooleanExpressionNode> toToken(String value) {
        return new OrToken();
    }

    public String getPattern() {
        return "\\+";
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
