package com.torstling.tdop.generic;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitespaceTokenType<N, S> implements TokenType<N, S> {
    private final String pattern;

    public WhitespaceTokenType() {
        this("\\s+");
    }

    public WhitespaceTokenType(String pattern) {
        this.pattern = pattern;
    }

    @NotNull
    @Override
    public Token<N, S> toToken(@NotNull LexingMatch match) {
        return new WhitespaceToken<>(this, match);
    }

    @NotNull
    @Override
    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

    @Override
    public boolean include() {
        return false;
    }
}
