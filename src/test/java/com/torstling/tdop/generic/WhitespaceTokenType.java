package com.torstling.tdop.generic;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitespaceTokenType<N> implements TokenType<N> {
    private final String pattern;

    public WhitespaceTokenType() {
        this.pattern = "\\s+";
    }

    @NotNull
    @Override
    public Token<N> toToken(@NotNull LexingMatch match) {
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
