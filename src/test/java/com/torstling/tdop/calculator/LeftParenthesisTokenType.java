package com.torstling.tdop.calculator;

import com.torstling.tdop.LexingMatch;
import com.torstling.tdop.Node;
import com.torstling.tdop.Token;
import com.torstling.tdop.TokenType;
import org.jetbrains.annotations.NotNull;

public class LeftParenthesisTokenType<N extends Node> implements TokenType<N> {

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new LeftParenthesisToken<>(match);
    }

    @NotNull
    public String getPattern() {
        return "\\(";
    }

    public static <N extends Node> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<>();
    }
}
