package com.torstling.tdop;

public interface TokenType {
    abstract Token toToken(final String value);
    public String getPattern();
}
