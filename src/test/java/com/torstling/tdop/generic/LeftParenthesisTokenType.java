package com.torstling.tdop.generic;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class LeftParenthesisTokenType<N extends AstNode> implements TokenType<N> {

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new LeftParenthesisToken<>(match);
    }

    @NotNull
    public String getPattern() {
        return "\\(";
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static <N extends AstNode> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<>();
    }
}
