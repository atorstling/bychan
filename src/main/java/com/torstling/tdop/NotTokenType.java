package com.torstling.tdop;

public class NotTokenType implements TokenType<BooleanExpressionNode> {

    public static final NotTokenType INSTANCE = new NotTokenType();

    public Token<BooleanExpressionNode> toToken(LexingMatch match) {
        return new NotToken();
    }

    public String getPattern() {
        return "\\!";
    }

    public static NotTokenType get() {
        return INSTANCE;
    }
}
