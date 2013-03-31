package com.torstling.tdop;

import org.jetbrains.annotations.NotNull;

public class VariableTokenType implements TokenType<BooleanExpressionNode> {

    public static final VariableTokenType INSTANCE = new VariableTokenType();

    public Token<BooleanExpressionNode> toToken(LexingMatch match) {
        return VariableToken.valueOf(match);
    }

    public String getPattern() {
        return "[a-z]+";
    }

    @NotNull
    public static VariableTokenType get() {
        return INSTANCE;
    }
}
