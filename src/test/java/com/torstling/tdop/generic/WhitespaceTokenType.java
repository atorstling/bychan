package com.torstling.tdop.generic;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class WhitespaceTokenType<N extends AstNode> implements TokenType<N> {
    private final String pattern;

    public WhitespaceTokenType() {
        this("\\s+");
    }

    public WhitespaceTokenType(String pattern) {
        this.pattern = pattern;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new WhitespaceToken<N>(this, match);
    }

    @NotNull
    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean shouldSkip() {
        return true;
    }
}