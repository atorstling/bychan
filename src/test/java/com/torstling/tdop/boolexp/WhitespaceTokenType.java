package com.torstling.tdop.boolexp;

import com.torstling.tdop.core.*;
import org.jetbrains.annotations.NotNull;

public class WhitespaceTokenType<N extends AstNode> implements TokenType<N> {
    private String pattern;

    public WhitespaceTokenType() {
        this("\\s+");
    }

    public WhitespaceTokenType(String pattern) {
        this.pattern = pattern;
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull LexingMatch match) {
        throw new ParsingFailedException("Cannot parse whitespace", match);
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
