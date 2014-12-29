package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenStack<N> {
    @NotNull
    private final ArrayDeque<Token<N>> tokens;
    @Nullable
    private Token<N> previous;

    @SafeVarargs
    @TestOnly
    TokenStack(Token<N>... tokens) {
        this(Arrays.asList(tokens));
    }

    public TokenStack(List<Token<N>> tokens) {
        this.tokens = new ArrayDeque<>(tokens);
        previous = null;
    }

    public Token<N> pop() {
        Token<N> popped = tokens.pop();
        previous = popped;
        return popped;
    }

    public Token<N> peek() {
        return tokens.peek();
    }

    @Nullable
    public Token<N> previous() {
        return previous;
    }

    @NotNull
    public List<Token<N>> remaining() {
        return new ArrayList<>(tokens);
    }
}
