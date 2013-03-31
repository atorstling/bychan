package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class NotTokenType implements TokenType<BooleanExpressionNode> {

    private static final NotTokenType INSTANCE = new NotTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new NotToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\!";
    }

    public static NotTokenType get() {
        return INSTANCE;
    }
}
