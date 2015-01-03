package org.bychan.core.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexemeStack<N> {
    public static final LexemeStack<Object> EMPTY = new LexemeStack<>();
    @NotNull
    private final ArrayDeque<Lexeme<N>> lexemes;
    @Nullable
    private Lexeme<N> previous;

    @SafeVarargs
    @TestOnly
    public LexemeStack(Lexeme<N>... lexemes) {
        this(Arrays.asList(lexemes));
    }

    public LexemeStack(List<Lexeme<N>> lexemes) {
        this.lexemes = new ArrayDeque<>(lexemes);
        previous = null;
    }

    public Lexeme<N> pop() {
        Lexeme<N> popped = lexemes.pop();
        previous = popped;
        return popped;
    }

    public Lexeme<N> peek() {
        return lexemes.peek();
    }

    @Nullable
    public Lexeme<N> previous() {
        return previous;
    }

    @NotNull
    public List<Lexeme<N>> remaining() {
        return new ArrayList<>(lexemes);
    }

    @NotNull
    public static <N> LexemeStack<N> empty() {
        //noinspection unchecked
        return (LexemeStack<N>) EMPTY;
    }
}
