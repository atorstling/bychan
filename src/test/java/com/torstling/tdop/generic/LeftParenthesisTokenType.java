package com.torstling.tdop.generic;

import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LeftParenthesisTokenType<N, S> implements TokenType<N, S> {

    @NotNull
    public Token<N, S> toToken(@NotNull LexingMatch match) {
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

    public static <N, S> LeftParenthesisTokenType<N, S> get() {
        return new LeftParenthesisTokenType<>();
    }
}
