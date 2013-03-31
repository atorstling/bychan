package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class AndTokenType implements TokenType<BooleanExpressionNode> {

    private static final AndTokenType INSTANCE = new AndTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return new AndToken(match);
    }

    @NotNull
    public String getPattern() {
        return "\\*";
    }

    @NotNull
    public static AndTokenType get() {
        return INSTANCE;
    }
}
