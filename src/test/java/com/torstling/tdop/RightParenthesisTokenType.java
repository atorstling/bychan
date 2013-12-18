package com.torstling.tdop;

import com.torstling.tdop.core.AstNode;
import com.torstling.tdop.core.LexingMatch;
import com.torstling.tdop.core.Token;
import com.torstling.tdop.core.TokenType;
import org.jetbrains.annotations.NotNull;

public class RightParenthesisTokenType<N extends AstNode> implements TokenType<N> {

    private static final RightParenthesisTokenType INSTANCE = new RightParenthesisTokenType<>();

    @NotNull
    public Token<N> toToken(@NotNull LexingMatch match) {
        return new RightParenthesisToken<>(match);
    }

    @NotNull
    public String getPattern() {
        return "\\)";
    }

    @Override
    public boolean shouldSkip() {
        return false;
    }

    public static <N extends AstNode> RightParenthesisTokenType<N> get() {
        //noinspection unchecked
        return (RightParenthesisTokenType<N>) INSTANCE;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
