package com.torstling.tdop;

import com.sun.istack.internal.NotNull;

public class OrTokenType implements TokenType {
    public static final OrTokenType INSTANCE = new OrTokenType();

    public Token toToken(String value) {
        return new OrToken();
    }

    public String getPattern() {
        return "\\|";
    }

    @NotNull
    public static OrTokenType get() {
        return INSTANCE;
    }
}
