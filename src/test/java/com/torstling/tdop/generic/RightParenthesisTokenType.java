package com.torstling.tdop.generic;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class RightParenthesisTokenType<N extends AstNode, S> implements TokenType<N, S> {

    private static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType<>();

    @NotNull
    public Token<N, S> toToken(@NotNull LexingMatch match) {
        return new RightParenthesisToken<N, S>(match);
    }

    @NotNull
    public Pattern getPattern() {
        return Pattern.compile("\\)");
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static <N extends AstNode, S> RightParenthesisTokenType<N, S> get() {
        //noinspection unchecked
        return (RightParenthesisTokenType<N, S>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
