package com.torstling.bychan.generic;

import com.torstling.bychan.core.LexingMatch;
import com.torstling.bychan.core.Token;
import com.torstling.bychan.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LeftParenthesisTokenType<N> implements TokenType<N> {

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new LeftParenthesisToken<>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\(");
    }

    @Override
    public boolean include() {
        return true;
    }

    public static <N> LeftParenthesisTokenType<N> get() {
        return new LeftParenthesisTokenType<>();
    }
}
