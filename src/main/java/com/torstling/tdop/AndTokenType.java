package com.torstling.tdop;

public class AndTokenType implements TokenType<BooleanExpressionNode> {

    public static final AndTokenType INSTANCE = new AndTokenType();

    public Token<BooleanExpressionNode> toToken(String value) {
        return new AndToken();
    }

    public String getPattern() {
        return "\\*";
    }

    public static AndTokenType get() {
        return INSTANCE;
    }
}
