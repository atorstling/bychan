package com.torstling.tdop;

public interface TokenType<N extends Node> {
    abstract Token<N> toToken(final String value);
    public String getPattern();
}
