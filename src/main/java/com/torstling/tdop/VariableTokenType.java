package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class VariableTokenType implements TokenType<BooleanExpressionNode> {

    private static final VariableTokenType INSTANCE = new VariableTokenType();

    @NotNull
    public Token<BooleanExpressionNode> toToken(@NotNull LexingMatch match) {
        return VariableToken.valueOf(match);
    }

    @NotNull
    public String getPattern() {
        return "[a-z]+";
    }

    @NotNull
    public static VariableTokenType get() {
        return INSTANCE;
    }
}
