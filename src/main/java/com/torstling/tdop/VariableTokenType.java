package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class VariableTokenType implements TokenType {

    public static final VariableTokenType INSTANCE = new VariableTokenType();

    public Token toToken(String value) {
        return VariableToken.valueOf(value);
    }

    public String getPattern() {
        return "[a-z]+";
    }

    @NotNull
    public static VariableTokenType get() {
        return INSTANCE;
    }
}
